package undefined.muscle_up.muscleup.service.graph;

import undefined.muscle_up.muscleup.entitys.graph.Graph;
import undefined.muscle_up.muscleup.entitys.graph.enums.GraphType;
import undefined.muscle_up.muscleup.payload.request.GraphRequest;
import undefined.muscle_up.muscleup.payload.response.GraphResponse;

import java.util.List;

public interface GraphService {

    void createGraph(GraphRequest graphCreateRequest);
    List<GraphResponse> getGraph(GraphType graphType);
    void deleteGraph(Integer graphId);
    void updateGraph(GraphRequest graphUpdateRequest, Integer graphId);
}
