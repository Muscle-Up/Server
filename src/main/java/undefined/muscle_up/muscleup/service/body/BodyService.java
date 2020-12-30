package undefined.muscle_up.muscleup.service.body;

import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.payload.request.BodyUpdateRequest;
import undefined.muscle_up.muscleup.payload.response.BodyResponse;

import java.util.List;

public interface BodyService {
    void bodyCreate(String title, String content, MultipartFile images);
    void bodyUpdate(BodyUpdateRequest bodyUpdateRequest, Integer bodyId);
    void bodyDelete(Integer bodyId);
    List<BodyResponse> getBodyList();
    void bodyImageUpdate(MultipartFile image, Integer bodyId);
    void bodyImageDelete(String ImageName);
    void addBodyImage(MultipartFile images, Integer bodyId);
    byte[] getBodyImage(String imageName);
}
