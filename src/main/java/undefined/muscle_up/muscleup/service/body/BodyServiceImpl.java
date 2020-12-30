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
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
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
public class BodyServiceImpl implements BodyService{
    private final BodyRepository bodyRepository;
    private final BodyImageRepository bodyImageRepository;
    private final UserRepository userRepository;

    private final AuthenticationFacade authenticationFacade;

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if(value != null) {
            setter.accept(value);
        }
    }

    @Value("${body.image.upload.dir}")
    private String bodyImageDirPath;

    @SneakyThrows
    @Override
    public void bodyCreate(String title, String content, MultipartFile[] images) {
        Integer receiptCode = authenticationFacade.getId();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Body body = bodyRepository.save(
                Body.builder()
                        .userId(receiptCode)
                        .createdAt(LocalDate.now())
                        .title(title)
                        .content(content)
                        .build()
        );

        for(MultipartFile image : images) {
            String imageName = UUID.randomUUID().toString();

            bodyImageRepository.save(
                    BodyImage.builder()
                            .imageName(image.isEmpty() ? "null" : imageName)
                            .bodyId(body.getId())
                            .build()
            );
            if (!image.isEmpty()) image.transferTo(new File(bodyImageDirPath, imageName));
        }
    }

    @Override
    public List<BodyResponse> getBodyList() {
        Integer receiptCode = authenticationFacade.getId();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        List<Body> bodyList = bodyRepository.findAllByUserId(receiptCode);
        List<BodyResponse> responses = new ArrayList<>();


        for(Body body : bodyList) {
            List<BodyImage> images = bodyImageRepository.findAllByBodyId(body.getId());
            List<String> imageNames = new ArrayList<>();

            for(BodyImage bodyImage : images) {
                imageNames.add(bodyImage.getImageName());
            }

            BodyResponse bodyResponse = BodyResponse.builder()
                    .bodyId(body.getId())
                    .title(body.getTitle())
                    .content(body.getContent())
                    .createdAt(body.getCreatedAt())
                    .imageNames(imageNames)
                    .build();

            responses.add(bodyResponse);
        }
        Collections.reverse(responses);

        return responses;
    }

    @SneakyThrows
    @Override
    public byte[] getBodyImage(String imageName) {
        Integer receiptCode = authenticationFacade.getId();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        File file = new File(bodyImageDirPath, imageName);
        if(!file.exists()) throw new RuntimeException();

        InputStream input = new FileInputStream(file);

        return IOUtils.toByteArray(input);
    }

    @Override
    public void bodyDelete(Integer bodyId) {
        Integer receiptCode = authenticationFacade.getId();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        bodyRepository.findById(bodyId)
                .orElseThrow(RuntimeException::new);

        BodyImage bodyImage = bodyImageRepository.findByBodyId(bodyId)
                .orElseThrow(RuntimeException::new);

        new File(bodyImageDirPath, bodyImage.getImageName()).delete();

        bodyImageRepository.deleteById(bodyImage.getId());

        bodyRepository.deleteById(bodyId);
    }

    @SneakyThrows
    @Override
    @Transactional
    public void bodyImageDelete(String imageName) {
        Integer receiptCode = authenticationFacade.getId();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        BodyImage bodyImage = bodyImageRepository.findByImageName(imageName)
                .orElseThrow(RuntimeException::new);

        new File(bodyImageDirPath, bodyImage.getImageName()).delete();

        bodyImageRepository.deleteById(bodyImage.getId());
    }

    @Override
    public void bodyUpdate(BodyUpdateRequest bodyUpdateRequest, Integer bodyId) {
        Integer receiptCode = authenticationFacade.getId();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Body body = bodyRepository.findById(bodyId)
                .orElseThrow(RuntimeException::new);

        setIfNotNull(body::setTitle, bodyUpdateRequest.getTitle());
        setIfNotNull(body::setContent, bodyUpdateRequest.getContent());

        bodyRepository.save(body);
    }

    @SneakyThrows
    @Override
    public void addBodyImage(MultipartFile[] images, Integer bodyId) {
        Integer userId = authenticationFacade.getId();
        userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        for(MultipartFile image : images) {
            String imageName = UUID.randomUUID().toString();

            bodyImageRepository.save(
                    BodyImage.builder()
                    .bodyId(bodyId)
                    .imageName(image.isEmpty() ? "null" : imageName)
                    .build()
            );

            image.transferTo(new File(bodyImageDirPath, imageName));
        }
    }

    @SneakyThrows
    @Override
    public void bodyImageUpdate(MultipartFile image, String name) {
        Integer receiptCode = authenticationFacade.getId();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        String imageName = UUID.randomUUID().toString();

        BodyImage bodyImage = bodyImageRepository.findByImageName(name)
                .orElseThrow(RuntimeException::new);
        File file = new File(bodyImageDirPath, bodyImage.getImageName());
        if(file.exists()) file.delete();

        bodyImageRepository.save(bodyImage.update(imageName));

        image.transferTo(new File(bodyImageDirPath, imageName));
    }

}

