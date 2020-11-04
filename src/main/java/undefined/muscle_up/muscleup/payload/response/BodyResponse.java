package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class BodyResponse {
    private Integer bodyId;
    private String title;
    private String content;
    private LocalDate createdAt;
}
