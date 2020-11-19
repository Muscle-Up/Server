package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GraphResponse {
    private Integer id;
    private double value;
}

