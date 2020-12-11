package undefined.muscle_up.muscleup.service.graph;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.graph.Graph;
import undefined.muscle_up.muscleup.entitys.graph.enums.GraphType;
import undefined.muscle_up.muscleup.entitys.graph.repository.GraphRepository;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.payload.request.GraphRequest;
import undefined.muscle_up.muscleup.payload.response.GraphResponse;
import undefined.muscle_up.muscleup.security.auth.AuthenticationFacade;

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

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if(!value.equals(0.0)) {
            setter.accept(value);
        }
    }

    private double getCheckAndValue(GraphType type, Graph graph) {
        if(type.equals(GraphType.muscleMass))
            return graph.getMuscleMass();
        else if(type.equals(GraphType.bodyFatMass))
            return graph.getBodyFatMass();
        else if(type.equals(GraphType.weight))
            return graph.getWeight();
        else
            throw new RuntimeException();
    }

    @Override
    public boolean checkGraphToday() {
    public void createGraph(GraphRequest graphCreateRequest) {
        Integer receiptCode = authenticationFacade.getId();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        List<Graph> graphList = graphRepository.findAllByUserId(receiptCode);

        for(Graph graph : graphList) {
            if(graph.getCreateAt().isEqual(LocalDate.now()))
                return true;
        }
        return false;
    }

    @Overridez
    public List<GraphResponse> getGraph(GraphType graphType) {
        Integer receiptCode = authenticationFacade.getId();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        List<Graph> graphs = graphRepository.findAllByUserId(receiptCode);
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
        Integer receiptCode = authenticationFacade.getId();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        graphRepository.save(
                Graph.builder()
                .userId(receiptCode)
                .weight(graphCreateRequest.getWeight())
                .bodyFatMass(graphCreateRequest.getBodyFatMass())
                .muscleMass(graphCreateRequest.getMuscleMass())
                .createAt(LocalDate.now())
                .build()
        );
    }

    @Override
    public void updateGraph(GraphRequest graphUpdateRequest, Integer graphId) {
        Integer receiptCode = authenticationFacade.getId();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Graph graph = graphRepository.findById(graphId)
                .orElseThrow(RuntimeException::new);

        setIfNotNull(graph::setMuscleMass, graphUpdateRequest.getMuscleMass());
        setIfNotNull(graph::setBodyFatMass, graphUpdateRequest.getBodyFatMass());
        setIfNotNull(graph::setWeight, graphUpdateRequest.getWeight());

        graphRepository.save(graph);
    }

    @Override
    public void deleteGraph(Integer graphId) {
        Integer receiptCode = authenticationFacade.getId();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Graph graph = graphRepository.findById(graphId)
                .orElseThrow(RuntimeException::new);

        graphRepository.deleteById(graph.getId());
    }
}

