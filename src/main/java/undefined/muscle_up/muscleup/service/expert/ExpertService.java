package undefined.muscle_up.muscleup.service.expert;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.payload.request.RegistrationRequest;
import undefined.muscle_up.muscleup.payload.response.expert_page.MyExpertPageResponse;
import undefined.muscle_up.muscleup.payload.response.PageResponse;
import undefined.muscle_up.muscleup.payload.response.expert_page.TargetExpertPageResponse;

public interface ExpertService {
    PageResponse expertList(Pageable page);
    void registration(RegistrationRequest registrationRequest);
    void deleteExpert();
    void updateImage(MultipartFile image);
    MyExpertPageResponse myExpertPage();
    TargetExpertPageResponse targetExpertPage(Integer expertId);
}
