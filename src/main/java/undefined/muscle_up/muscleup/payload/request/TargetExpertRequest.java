package undefined.muscle_up.muscleup.payload.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.entitys.user.enums.Sex;

import java.time.LocalDate;

@Getter
@Builder
public class TargetExpertRequest {
    private String name;
    private String introduction;
    private Integer age;
    private Sex sex;
    private MultipartFile userImage;
    private String certificateName;
    private LocalDate acquisitionDate;
    private MultipartFile certificateImage;
}
