package undefined.muscle_up.muscleup.payload.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Builder
public class RegistrationRequest {
    private String introduction;
    private String certificateName;
    private LocalDate acquisitionDate;
    private MultipartFile certificateImage;
}
