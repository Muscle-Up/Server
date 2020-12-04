package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import undefined.muscle_up.muscleup.payload.request.GraphRequest;
import undefined.muscle_up.muscleup.payload.response.GraphResponse;
import undefined.muscle_up.muscleup.service.graph.GraphService;

import java.util.List;

@RestController
@RequestMapping("/graph")
@RequiredArgsConstructor
public class GraphController {

    private final GraphService graphService;

    @GetMapping
    public List<GraphResponse> getGraph() {
        return graphService.getGraph();
    }

    @PostMapping
    public void createGraph(@RequestBody GraphRequest graphCreateRequest) {
        graphService.createGraph(graphCreateRequest);
    }

    @PutMapping("/{graphId}")
    public void updateGraph(@PathVariable Integer graphId,
                            @RequestBody GraphRequest graphRequest) {
        graphService.updateGraph(graphRequest, graphId);
    }

    @DeleteMapping("/{graphId}")
    public void deleteGraph(@PathVariable Integer graphId) {
        graphService.deleteGraph(graphId);
    }
}

