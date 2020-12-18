package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GraphResponse {
    private Integer id;
    private double value;
    private LocalDate createAt;
}

