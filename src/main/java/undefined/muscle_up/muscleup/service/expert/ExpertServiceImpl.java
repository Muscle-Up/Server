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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService{

    private final UserRepository userRepository;
    private final MasterImageRepository masterImageRepository;

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
    public void registration(String email, String introduction, MultipartFile image) {
        User user = userRepository.findAllByEmail(email).orElseThrow(RuntimeException::new);

        userRepository.save(user.updateType(UserType.EXPERT));

        userRepository.save(
                User.builder()
                    .introduction(introduction)
                    .build()
        );

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
    public void deleteExpert(String email) {
        User user = userRepository.findAllByEmail(email).orElseThrow(RuntimeException::new);

        userRepository.save(user.updateType(UserType.USER));

        masterImageRepository.deleteByUserId(user.getId()).orElseThrow(RuntimeException::new);

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
        User user = userRepository.findById(1).orElseThrow(RuntimeException::new);

        String fileName = UUID.randomUUID().toString();

        MasterImage masterImage = masterImageRepository.findByUserId(user.getId()).orElseThrow(RuntimeException::new);
        masterImageRepository.save(masterImage.update(fileName));

        image.transferTo(new File(imageDirPath, fileName));

    }

}
