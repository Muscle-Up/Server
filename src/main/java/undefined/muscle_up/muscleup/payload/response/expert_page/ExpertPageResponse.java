package undefined.muscle_up.muscleup.payload.response.expert_page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import undefined.muscle_up.muscleup.entitys.user.enums.UserType;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExpertPageResponse {
    protected String name;
    protected String introduction;
    protected Integer age;
    protected UserType type;
    protected String userImage;
    protected String certificateName;
    protected LocalDate acquisitionDate;
    protected String certificateImage;
}
