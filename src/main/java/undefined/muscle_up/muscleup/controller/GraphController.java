package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import undefined.muscle_up.muscleup.entitys.graph.enums.GraphType;
import undefined.muscle_up.muscleup.payload.request.GraphRequest;
import undefined.muscle_up.muscleup.payload.response.GraphResponse;
import undefined.muscle_up.muscleup.service.graph.GraphService;

import java.util.List;

@RestController
@RequestMapping("/graph")
@RequiredArgsConstructor
public class GraphController {

    private final GraphService graphService;

    @GetMapping("/{graphType}")
    public List<GraphResponse> getGraph(@PathVariable GraphType graphType ){
        return graphService.getGraph(graphType);
    }

    @GetMapping("/check")
    public boolean getCheckToday() {
        return graphService.checkGraphToday();
    }

    @PostMapping
    public void createGraph(@RequestBody GraphRequest graphCreateRequest) {
        graphService.createGraph(graphCreateRequest);
    }

    @PutMapping("/{graphId}")
    public void updateGraph(@PathVariable Integer graphId,
                            @RequestBody GraphRequest graphUpdateRequest) {
        graphService.updateGraph(graphUpdateRequest, graphId);
    }

    @DeleteMapping("/{graphId}")
    public void deleteGraph(@PathVariable Integer graphId) {
        graphService.deleteGraph(graphId);
    }
}

