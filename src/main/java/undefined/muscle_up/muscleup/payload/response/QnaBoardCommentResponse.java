package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;
import undefined.muscle_up.muscleup.entitys.comment.SubComment;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class QnaBoardCommentResponse {

    private Integer commendId;

    private String content;

    private String writer;

    private LocalDateTime createdAt;

    private List<QnaBoardSubCommentResponse> subComment;

    private boolean isMine;
}
