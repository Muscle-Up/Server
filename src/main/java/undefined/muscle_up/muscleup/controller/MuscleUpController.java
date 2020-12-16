package undefined.muscle_up.muscleup.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("muscleup")
public class MuscleUpController {

    @GetMapping
    public String muscleUp(@RequestParam String param) {
        return param;
    }
}
