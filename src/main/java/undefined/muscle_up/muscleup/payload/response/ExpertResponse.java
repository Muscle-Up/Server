package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpertResponse {

    private String introduction;

    private String name;
}
