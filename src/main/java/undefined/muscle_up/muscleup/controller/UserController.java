package undefined.muscle_up.muscleup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.entitys.user.enums.Sex;
import undefined.muscle_up.muscleup.entitys.user.enums.UserType;
import undefined.muscle_up.muscleup.payload.request.SignUpRequest;
import undefined.muscle_up.muscleup.service.user.UserService;

import javax.validation.constraints.Email;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void signUp(@RequestParam String name,
                       @RequestParam Integer age,
                       @RequestParam Sex sex,
                       @RequestParam @Email String email,
                       @RequestParam String password,
                       @RequestParam Integer height,
                       @RequestParam Integer weight,
                       @RequestParam MultipartFile image){

        userService.signUp(
                SignUpRequest.builder()
                        .name(name)
                        .age(age)
                        .sex(sex)
                        .email(email)
                        .password(password)
                        .height(height)
                        .weight(weight)
                        .image(image)
                        .build()
        );
    }

}
