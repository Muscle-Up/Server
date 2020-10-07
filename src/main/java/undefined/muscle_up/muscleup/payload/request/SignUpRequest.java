package undefined.muscle_up.muscleup.payload.request;

import lombok.Getter;
import undefined.muscle_up.muscleup.entitys.user.enums.Sex;

@Getter
public class SignUpRequest {

    private String name;

    private Integer age;

    private Sex sex;

    private String email;

    private String password;

    private Integer height;

    private Integer weight;

}
