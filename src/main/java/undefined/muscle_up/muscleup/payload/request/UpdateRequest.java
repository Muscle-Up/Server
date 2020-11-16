package undefined.muscle_up.muscleup.payload.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class UpdateRequest {
    private String name;
    private Integer age;
    private Integer height;
    private Integer weight;
    private MultipartFile image;
}
