package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QnaBoardCommentResponse {

    private Integer commendId;

    private String content;

    private String writer;

    private LocalDateTime createdAt;

    private boolean isMine;
}
