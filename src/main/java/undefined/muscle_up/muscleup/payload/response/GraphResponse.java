package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GraphResponse {
    private Integer id;
    private double tall;
    private double weight;
    private double muscular_strength;
    private double fat_percentage;
}

