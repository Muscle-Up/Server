package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QnaBoardListResponse {

    private Integer boardId;

    private String title;

    private String writer;

    private LocalDateTime createdAt;
}
