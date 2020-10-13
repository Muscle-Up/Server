package undefined.muscle_up.muscleup.service.user;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.image.UserImage;
import undefined.muscle_up.muscleup.entitys.image.repository.UserImageRepository;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.enums.UserType;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.payload.request.SignUpRequest;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${image.file.path}")
    private String imagePath;

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;

    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Override
    public void signUp(SignUpRequest signUpRequest) {

        userRepository.findByEmail(signUpRequest.getEmail()).ifPresent(user -> {
       throw new RuntimeException();
        });

        User user = userRepository.save(
                User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .name(signUpRequest.getName())
                .age(signUpRequest.getAge())
                .sex(signUpRequest.getSex())
                .type(UserType.USER)
                .heigth(signUpRequest.getHeigth())
                .weigth(signUpRequest.getWeigth())
                .build()
        );

        String fileName = UUID.randomUUID().toString();

        UserImage userImage = userImageRepository.save(
                UserImage.builder()
                    .userId(user.getId())
                    .imageName(fileName)
                    .build()
        );

        File file = new File(imagePath, userImage.getImageName());
        signUpRequest.getImage().transferTo(file);
    }

}
