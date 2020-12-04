package undefined.muscle_up.muscleup.service.graph;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.graph.Graph;
import undefined.muscle_up.muscleup.entitys.graph.repository.GraphRepository;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.payload.request.GraphRequest;
import undefined.muscle_up.muscleup.payload.response.GraphResponse;
import undefined.muscle_up.muscleup.security.auth.AuthenticationFacade;

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
        if(value.equals(0.0)) {
            System.out.println(value);
            return;
        }
        setter.accept(value);
    }

    @Override
    public void createGraph(GraphRequest graphCreateRequest) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        graphRepository.save(
                Graph.builder()
                        .tall(graphCreateRequest.getTall())
                        .weight(graphCreateRequest.getWeight())
                        .muscular_strength(graphCreateRequest.getMuscular_strength())
                        .fat_percentage(graphCreateRequest.getFat_percentage())
                        .userId(receiptCode)
                        .build()
        );
    }

    @Override
    public List<GraphResponse> getGraph() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        List<Graph> graphs = graphRepository.findAllByUserId(receiptCode);
        List<GraphResponse> responses = new ArrayList<>();

        for(Graph graph : graphs) {
            GraphResponse graphResponse =
                    GraphResponse.builder()
                            .id(graph.getId())
                            .tall(graph.getTall())
                            .weight(graph.getWeight())
                            .fat_percentage(graph.getFat_percentage())
                            .muscular_strength(graph.getMuscular_strength())
                            .build();

            responses.add(graphResponse);
        }

        return responses;
    }

    @Override
    public void deleteGraph(Integer graphId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Graph graph = graphRepository.findById(graphId)
                .orElseThrow(RuntimeException::new);

        graphRepository.deleteById(graph.getId());
    }

    @Override
    public void updateGraph(GraphRequest graphUpdateRequest, Integer graphId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Graph graph = graphRepository.findById(graphId)
                .orElseThrow(RuntimeException::new);

        setIfNotNull(graph::setTall, graphUpdateRequest.getTall());
        setIfNotNull(graph::setWeight, graphUpdateRequest.getWeight());
        setIfNotNull(graph::setMuscular_strength, graphUpdateRequest.getMuscular_strength());
        setIfNotNull(graph::setFat_percentage, graphUpdateRequest.getFat_percentage());

        graphRepository.save(graph);
    }
}

