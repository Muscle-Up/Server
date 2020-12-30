package undefined.muscle_up.muscleup.service.body;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.entitys.body.Body;
import undefined.muscle_up.muscleup.entitys.body.repository.BodyRepository;
import undefined.muscle_up.muscleup.entitys.image.BodyImage;
import undefined.muscle_up.muscleup.entitys.image.repository.BodyImageRepository;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.exceptions.BodyImageNotFoundException;
import undefined.muscle_up.muscleup.exceptions.BodyNotFoundException;
import undefined.muscle_up.muscleup.exceptions.UserNotFoundException;
import undefined.muscle_up.muscleup.payload.request.BodyUpdateRequest;
import undefined.muscle_up.muscleup.payload.response.BodyResponse;
import undefined.muscle_up.muscleup.security.auth.AuthenticationFacade;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class BodyServiceImpl implements BodyService {
    private final BodyRepository bodyRepository;
    private final BodyImageRepository bodyImageRepository;
    private final UserRepository userRepository;

    private final AuthenticationFacade authenticationFacade;

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }

    @Value("${body.image.upload.dir}")
    private String bodyImageDirPath;

    @SneakyThrows
    @Override
    public void bodyCreate(String title, String content, MultipartFile image) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        Body body = bodyRepository.save(
                Body.builder()
                        .userId(user.getId())
                        .createdAt(LocalDate.now())
                        .title(title)
                        .content(content)
                        .build()
        );

        String imageName = UUID.randomUUID().toString();

        bodyImageRepository.save(
                BodyImage.builder()
                        .imageName(image.isEmpty() ? "null" : imageName)
                        .bodyId(body.getId())
                        .build()
        );
        if (!image.isEmpty()) image.transferTo(new File(bodyImageDirPath, imageName));
    }

    @Override
    public List<BodyResponse> getBodyList() {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        List<Body> bodyList = bodyRepository.findAllByUserId(user.getId());
        List<BodyResponse> responses = new ArrayList<>();


        for (Body body : bodyList) {
            BodyImage bodyImage = bodyImageRepository.findByBodyId(body.getId())
                    .orElse(
                            BodyImage.builder()
                            .bodyId(body.getId())
                            .imageName("deleted")
                            .build()
                    );

            BodyResponse bodyResponse = BodyResponse.builder()
                    .bodyId(body.getId())
                    .title(body.getTitle())
                    .content(body.getContent())
                    .createdAt(body.getCreatedAt())
                    .imageName(bodyImage.getImageName())
                    .build();

            responses.add(bodyResponse);
        }
        Collections.reverse(responses);

        return responses;
    }

    @SneakyThrows
    @Override
    public byte[] getBodyImage(String imageName) {
        userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        File file = new File(bodyImageDirPath, imageName);
        if (!file.exists()) throw new BodyImageNotFoundException();

        InputStream input = new FileInputStream(file);

        return IOUtils.toByteArray(input);
    }

    @Override
    public void bodyDelete(Integer bodyId) {
        userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        bodyRepository.findById(bodyId)
                .orElseThrow(BodyNotFoundException::new);

        BodyImage bodyImage = bodyImageRepository.findByBodyId(bodyId)
                .orElseThrow(BodyImageNotFoundException::new);

        new File(bodyImageDirPath, bodyImage.getImageName()).delete();

        bodyImageRepository.deleteById(bodyImage.getId());

        bodyRepository.deleteById(bodyId);
    }

    @SneakyThrows
    @Override
    @Transactional
    public void bodyImageDelete(String imageName) {
        userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        BodyImage bodyImage = bodyImageRepository.findByImageName(imageName)
                .orElseThrow(BodyImageNotFoundException::new);
        new File(bodyImageDirPath, bodyImage.getImageName()).delete();

        bodyImageRepository.deleteById(bodyImage.getId());
    }

    @Override
    public void bodyUpdate(BodyUpdateRequest bodyUpdateRequest, Integer bodyId) {
        userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        Body body = bodyRepository.findById(bodyId)
                .orElseThrow(BodyNotFoundException::new);

        setIfNotNull(body::setTitle, bodyUpdateRequest.getTitle());
        setIfNotNull(body::setContent, bodyUpdateRequest.getContent());

        bodyRepository.save(body);
    }

    @SneakyThrows
    @Override
    public void addBodyImage(MultipartFile image, Integer bodyId) {
        userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        if(image.isEmpty())
            throw new BodyImageNotFoundException();

        String imageName = UUID.randomUUID().toString();

        bodyImageRepository.save(
                BodyImage.builder()
                        .bodyId(bodyId)
                        .imageName(imageName)
                        .build()
        );
        image.transferTo(new File(bodyImageDirPath, imageName));
    }

    @SneakyThrows
    @Override
    public void bodyImageUpdate(MultipartFile image, Integer bodyId) {
        userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        if(image.isEmpty()) throw new BodyImageNotFoundException();

        String imageName = UUID.randomUUID().toString();

        BodyImage bodyImage = bodyImageRepository.findByBodyId(bodyId)
                .orElseThrow(BodyImageNotFoundException::new);

        File file = new File(bodyImageDirPath, bodyImage.getImageName());
        if (file.exists()) file.delete();

        bodyImageRepository.save(bodyImage.update(imageName));

        image.transferTo(new File(bodyImageDirPath, imageName));
    }

}

