package undefined.muscle_up.muscleup.service.pose;

import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;

public interface PoseService {
    LinkedHashMap poseCoordinates(MultipartFile image);
}
