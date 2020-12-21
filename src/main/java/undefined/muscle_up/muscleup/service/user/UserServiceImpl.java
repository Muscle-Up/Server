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
import undefined.muscle_up.muscleup.payload.response.MainPageResponse;
import undefined.muscle_up.muscleup.payload.request.SignUpRequest;
import undefined.muscle_up.muscleup.payload.request.UpdateRequest;
import undefined.muscle_up.muscleup.security.auth.AuthenticationFacade;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final AuthenticationFacade authenticationFacade;
    private final PasswordEncoder passwordEncoder;

    @Value("${image.file.path}")
    private String imagePath;

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
                .height(signUpRequest.getHeight())
                .weight(signUpRequest.getWeight())
                .build()
        );

        String fileName = UUID.randomUUID().toString();

        UserImage userImage = userImageRepository.save(
                UserImage.builder()
                    .user(user)
                    .imageName(fileName)
                    .build()
        );

        File file = new File(imagePath, userImage.getImageName());
        signUpRequest.getImage().transferTo(file);
    }

    @Override
    public void changePw(String password) {
        Integer receiptCode = authenticationFacade.getId();
        User user = userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        userRepository.save(user.updatePw(passwordEncoder.encode(password)));
    }

    @SneakyThrows
    @Override
    public void updateUser(UpdateRequest updateRequest) {
        Integer receiptCode = authenticationFacade.getId();
        User user = userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        if (updateRequest.getImage() != null) {
            String fileName = UUID.randomUUID().toString();

            UserImage userImage = userImageRepository.findByUserId(user.getId())
                    .orElseThrow(RuntimeException::new);

            File file = new File(imagePath, userImage.getImageName());
            Files.deleteIfExists(file.toPath());

            userImageRepository.save(userImage.update(fileName));

            updateRequest.getImage().transferTo(new File(imagePath, fileName));
        }

        setIfNotNull(user::setAge, updateRequest.getAge());
        setIfNotNull(user::setName, updateRequest.getName());
        setIfNotNull(user::setHeight, updateRequest.getHeight());
        setIfNotNull(user::setWeight, updateRequest.getWeight());

        userRepository.save(user);

    }

    @Override
    public MainPageResponse mainPage() {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(RuntimeException::new);

        return MainPageResponse.builder()
                .name(user.getName())
                .age(user.getAge())
                .height(user.getHeight())
                .sex(user.getSex())
                .weight(user.getWeight())
                .build();
    }

    private <T> void setIfNotNull(Consumer<T> setter, T value){
        if(value != null){
            setter.accept(value);
        }
    }
}
