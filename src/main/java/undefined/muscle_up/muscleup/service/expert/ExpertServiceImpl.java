package undefined.muscle_up.muscleup.service.expert;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.entitys.image.MasterImage;
import undefined.muscle_up.muscleup.entitys.image.UserImage;
import undefined.muscle_up.muscleup.entitys.image.repository.MasterImageRepository;
import undefined.muscle_up.muscleup.entitys.image.repository.UserImageRepository;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.enums.UserType;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.payload.request.RegistrationRequest;
import undefined.muscle_up.muscleup.payload.response.ExpertResponse;
import undefined.muscle_up.muscleup.payload.response.PageResponse;
import undefined.muscle_up.muscleup.payload.response.expert_page.ExpertPageResponse;
import undefined.muscle_up.muscleup.payload.response.expert_page.MyExpertPageResponse;
import undefined.muscle_up.muscleup.payload.response.expert_page.TargetExpertPageResponse;
import undefined.muscle_up.muscleup.security.auth.AuthenticationFacade;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService{

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final MasterImageRepository masterImageRepository;

    private final AuthenticationFacade authenticationFacade;

    @Value("${image.upload.dir}")
    private String imageDirPath;

    @Override
    public PageResponse expertList(Pageable page) {
        Page<User> users = userRepository.findAllByType(UserType.EXPERT, page);

        Integer totalPage = users.getTotalPages();
        Integer totalElements = users.getNumberOfElements();

        List<ExpertResponse> expertResponses = new ArrayList<>();

        for (User user : users) {
            expertResponses.add(
                    ExpertResponse.builder()
                        .uuid(user.getId())
                        .name(user.getName())
                        .introduction(user.getIntroduction())
                        .certificateName(user.getCertificateName())
                        .certificateImage(user.getMasterImage().getImageName())
                        .build()
            );
        }

        return PageResponse.builder()
                    .totalPages(totalPage)
                    .totalElements(totalElements)
                    .expertListResponse(expertResponses)
                    .build();
    }

    @SneakyThrows
    @Override
    public void registration(RegistrationRequest registrationRequest) {
        Integer receiptCode = authenticationFacade.getId();
        User user = userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        if (user.getType().equals(UserType.EXPERT)) throw new RuntimeException();

        userRepository.save(user.update(registrationRequest.getIntroduction(),
                                        UserType.EXPERT,
                                        registrationRequest.getCertificateName(),
                                        registrationRequest.getAcquisitionDate()));

        String fileName = UUID.randomUUID().toString();

        masterImageRepository.save(
                MasterImage.builder()
                    .user(user)
                    .imageName(fileName)
                    .build()
        );

        registrationRequest.getCertificateImage().transferTo(new File(imageDirPath, fileName));
    }

    @SneakyThrows
    @Override
    @Transactional
    public void deleteExpert() {
        Integer receiptCode = authenticationFacade.getId();
        User user = userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        userRepository.save(user.update("", UserType.USER, "", null));

        MasterImage masterImage = masterImageRepository.findByUserId(user.getId())
               .orElseThrow(RuntimeException::new);

        masterImageRepository.delete(masterImage);
        Files.delete(new File(imageDirPath, masterImage.getImageName()).toPath());
    }

    @SneakyThrows
    @Override
    public void updateImage(MultipartFile image) {
        Integer receiptCode = authenticationFacade.getId();
        User user = userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        String fileName = UUID.randomUUID().toString();

        MasterImage masterImage = masterImageRepository.findByUserId(user.getId())
                .orElseThrow(RuntimeException::new);

        File file = new File(imageDirPath, masterImage.getImageName());
        if (file.exists()) file.delete();

        masterImageRepository.save(masterImage.update(fileName));

        image.transferTo(new File(imageDirPath, fileName));
    }

    @Override
    public MyExpertPageResponse myExpertPage() {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(RuntimeException::new);

        return getExpertPage(user, MyExpertPageResponse.class);
    }

    @Override
    public TargetExpertPageResponse targetExpertPage(Integer expertId) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(RuntimeException::new);

        User target = userRepository.findById(expertId)
                .orElseThrow(RuntimeException::new);

        TargetExpertPageResponse response = getExpertPage(target, TargetExpertPageResponse.class);
        response.isMine(user.equals(target));

        return response;
    }

    @SneakyThrows
    private <T extends ExpertPageResponse> T getExpertPage(User user, Class<T> tClass) {
        UserImage userImage = userImageRepository.findById(user.getId())
                .orElseThrow(RuntimeException::new);

        MasterImage masterImage = masterImageRepository.findByUserId(user.getId())
                .orElseThrow(RuntimeException::new);

        return tClass.cast(
                tClass.getConstructor(ExpertPageResponse.class).newInstance(
                        ExpertPageResponse.builder()
                                .name(user.getName())
                                .introduction(user.getIntroduction())
                                .age(user.getAge())
                                .type(user.getType())
                                .certificateName(user.getCertificateName())
                                .acquisitionDate(user.getAcquisitionDate())
                                .userImage(userImage.getImageName())
                                .certificateImage(masterImage.getImageName())
                                .build()
                )
        );
    }

}
