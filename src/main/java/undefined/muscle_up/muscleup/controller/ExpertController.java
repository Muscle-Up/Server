package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import undefined.muscle_up.muscleup.payload.request.ExpertRegistrationRequest;
import undefined.muscle_up.muscleup.service.expert.ExpertService;

@RestController
@RequestMapping("expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;

    @PostMapping
    public void registration(@RequestBody ExpertRegistrationRequest expertRegistration) {
        expertService.registration(expertRegistration);
    }
}
