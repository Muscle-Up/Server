package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QnaBoardSubCommentResponse {

    private Integer subCommentId;

    private String content;

    private String writer;

    private LocalDateTime createdAt;

    private boolean isMine;
}
