package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class BodyResponse {
    private Integer bodyId;
    private String title;
    private String content;
    private String imageName;
    private LocalDate createdAt;
}
