package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.payload.request.SignUpRequest;
import undefined.muscle_up.muscleup.service.user.UserService;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void signUp(@RequestParam SignUpRequest signUpRequest, MultipartFile image){
        userService.signUp(signUpRequest);
    }

}
