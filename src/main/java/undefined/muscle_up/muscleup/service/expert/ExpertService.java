package undefined.muscle_up.muscleup.service.expert;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.payload.response.PageResponse;

public interface ExpertService {

    PageResponse expertList(Pageable page);
    void registration(String introduction, MultipartFile image);
    void deleteExpert();
    byte[] getImage(String imageName);
    void updateImage(MultipartFile image);
}
