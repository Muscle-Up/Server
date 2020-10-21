package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class QnaBoardContentResponse {

    private String title;

    private String content;

    private String writer;

    private Integer view;

    private Integer likeCount;

    private List<String> images;

    private LocalDateTime createdAt;

    private boolean mine;

    private boolean likeTF;
}
