package undefined.muscle_up.muscleup.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageCoordinatesResponse {
    private Float area;
    private Float[] bbox;
    private int category_id;
    private Float[] keypoints;
    private Float score;
}
