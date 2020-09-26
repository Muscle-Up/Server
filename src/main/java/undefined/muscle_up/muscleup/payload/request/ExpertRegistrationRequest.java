package undefined.muscle_up.muscleup.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ExpertRegistrationRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private boolean isMaster;
}
