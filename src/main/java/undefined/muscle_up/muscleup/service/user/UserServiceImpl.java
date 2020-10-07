package undefined.muscle_up.muscleup.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.image.repository.UserImageRepository;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.payload.request.SignUpRequest;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        userRepository.save(
                User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .name(signUpRequest.getName())
                .age(signUpRequest.getAge())
                .sex(signUpRequest.getSex())
                .height(signUpRequest.getHeight())
                .weight(signUpRequest.getWeight())
                .build()
        );
    }
}
