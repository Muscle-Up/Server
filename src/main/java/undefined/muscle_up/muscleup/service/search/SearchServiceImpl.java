package undefined.muscle_up.muscleup.service.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.image.repository.MasterImageRepository;
import undefined.muscle_up.muscleup.entitys.image.repository.UserImageRepository;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.payload.response.ExpertResponse;
import undefined.muscle_up.muscleup.security.auth.AuthenticationFacade;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final MasterImageRepository masterImageRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<ExpertResponse> searchExpert(String certificateName) {
        Integer receiptCode = authenticationFacade.getId();
        userRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        List<User> target = userRepository.findByCertificateNameContains(certificateName);

        List<ExpertResponse> expertResponses = new ArrayList<>();
        for (User user : target) {
            expertResponses.add(
                    ExpertResponse.builder()
                        .uuid(user.getId())
                        .name(user.getName())
                        .certificateName(user.getCertificateName())
                        .introduction(user.getIntroduction())
                        .certificateImage(user.getMasterImage().getImageName())
                        .build()
            );

        }

        return expertResponses;
    }
}
