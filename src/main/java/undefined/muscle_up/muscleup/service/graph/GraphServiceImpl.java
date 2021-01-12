package undefined.muscle_up.muscleup.service.graph;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.graph.Graph;
import undefined.muscle_up.muscleup.entitys.graph.enums.GraphType;
import undefined.muscle_up.muscleup.entitys.graph.repository.GraphRepository;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.exceptions.CheckNotFoundException;
import undefined.muscle_up.muscleup.exceptions.GraphNotFoundException;
import undefined.muscle_up.muscleup.exceptions.UserNotFoundException;
import undefined.muscle_up.muscleup.payload.request.GraphRequest;
import undefined.muscle_up.muscleup.payload.response.GraphResponse;
import undefined.muscle_up.muscleup.security.auth.AuthenticationFacade;
import undefined.muscle_up.muscleup.util.converter.notnull.SetIfNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class GraphServiceImpl implements GraphService{

    private final UserRepository userRepository;
    private final  GraphRepository graphRepository;

    private final AuthenticationFacade authenticationFacade;
    private final SetIfNotNull setIfNotNull;

    private double getCheckAndValue(GraphType type, Graph graph) {
        if(type.equals(GraphType.muscleMass))
            return graph.getMuscleMass();
        else if(type.equals(GraphType.bodyFatMass))
            return graph.getBodyFatMass();
        else if(type.equals(GraphType.weight))
            return graph.getWeight();
        else
            throw new CheckNotFoundException();
    }

    @Override
    public boolean checkGraphToday() {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        List<Graph> graphList = graphRepository.findAllByUserId(user.getId());

        for(Graph graph : graphList) {
            if(graph.getCreateAt().isEqual(LocalDate.now()))
                return true;
        }
        return false;
    }

    @Override
    public List<GraphResponse> getGraph(GraphType graphType) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        List<Graph> graphs = graphRepository.findAllByUserId(user.getId());
        List<GraphResponse> responses = new ArrayList<>();

        for(Graph graph : graphs) {
            double value = getCheckAndValue(graphType, graph);
            GraphResponse graphResponse = GraphResponse.builder()
                    .id(graph.getId())
                    .value(value)
                    .createAt(graph.getCreateAt())
                    .build();
            responses.add(graphResponse);
        }
        return responses;
    }

    @Override
    public void createGraph(GraphRequest graphCreateRequest) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        graphRepository.save(
                Graph.builder()
                .userId(user.getId())
                .weight(graphCreateRequest.getWeight())
                .bodyFatMass(graphCreateRequest.getBodyFatMass())
                .muscleMass(graphCreateRequest.getMuscleMass())
                .createAt(LocalDate.now())
                .build()
        );
    }

    @Override
    public void updateGraph(GraphRequest graphUpdateRequest, Integer graphId) {
        userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        Graph graph = graphRepository.findById(graphId)
                .orElseThrow(GraphNotFoundException::new);

        setIfNotNull.minorityIfNotNull(graph::setMuscleMass, graphUpdateRequest.getMuscleMass());
        setIfNotNull.minorityIfNotNull(graph::setBodyFatMass, graphUpdateRequest.getBodyFatMass());
        setIfNotNull.minorityIfNotNull(graph::setWeight, graphUpdateRequest.getWeight());

        graphRepository.save(graph);
    }

    @Override
    public void deleteGraph(Integer graphId) {
        userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        Graph graph = graphRepository.findById(graphId)
                .orElseThrow(GraphNotFoundException::new);

        graphRepository.deleteById(graph.getId());
    }

}

