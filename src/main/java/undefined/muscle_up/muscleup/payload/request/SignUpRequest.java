package undefined.muscle_up.muscleup.payload.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import undefined.muscle_up.muscleup.entitys.user.enums.Sex;

@Getter
@Builder
public class SignUpRequest {
    private String name;
    private Integer age;
    private Sex sex;
    private String email;
    private String password;
    private Integer height;
    private Integer weight;
    private MultipartFile image;
}
