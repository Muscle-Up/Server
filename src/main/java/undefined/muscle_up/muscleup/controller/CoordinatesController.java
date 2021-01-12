package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.service.pose.PoseService;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/pose")
@RequiredArgsConstructor
public class CoordinatesController {

    private final PoseService poseService;

    @GetMapping
    public LinkedHashMap poseCoordinates(@RequestParam MultipartFile image) {
        return poseService.poseCoordinates(image);
    }
}
