package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;
import undefined.muscle_up.muscleup.entitys.user.enums.Sex;

@Getter
@Builder
public class MainPageResponse {
    private String name;
    private Integer age;
    private Sex sex;
    private Integer height;
    private Integer weight;
}
