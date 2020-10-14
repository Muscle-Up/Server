package undefined.muscle_up.muscleup.service.expert;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.entitys.image.MasterImage;
import undefined.muscle_up.muscleup.entitys.image.repository.MasterImageRepository;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.enums.UserType;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.payload.response.ExpertResponse;
import undefined.muscle_up.muscleup.payload.response.PageResponse;
import undefined.muscle_up.muscleup.security.auth.AuthenticationFacade;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService{

    private final UserRepository userRepository;
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
                        .introduction(user.getIntroduction())
                        .name(user.getName())
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
    public void registration(String introduction, MultipartFile image) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        if (user.getType().equals(UserType.EXPERT)) throw new RuntimeException();

        userRepository.save(user.update(introduction, UserType.EXPERT));

        String fileName = UUID.randomUUID().toString();

        masterImageRepository.save(
                MasterImage.builder()
                    .userId(user.getId())
                    .imageName(fileName)
                    .build()
        );

        image.transferTo(new File(imageDirPath, fileName));
    }

    @Override
    @Transactional
    public void deleteExpert() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        userRepository.save(user.update("",UserType.USER));

       MasterImage masterImage = masterImageRepository.deleteByUserId(user.getId())
               .orElseThrow(RuntimeException::new);

       new File(imageDirPath, masterImage.getImageName()).delete();
    }

    @SneakyThrows
    @Override
    public byte[] getImage(String imageName) {
        File file = new File(imageDirPath, imageName);
        if (!file.exists())
            throw new RuntimeException();

        InputStream inputStream = new FileInputStream(file);

        return IOUtils.toByteArray(inputStream);
    }

    @SneakyThrows
    @Override
    public void updateImage(MultipartFile image) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
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

}
