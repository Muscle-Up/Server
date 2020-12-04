package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import undefined.muscle_up.muscleup.payload.response.ExpertResponse;
import undefined.muscle_up.muscleup.service.search.SearchService;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public List<ExpertResponse> expertSearch(@RequestParam String certificateName) {
        return searchService.searchExpert(certificateName);
    }
}
