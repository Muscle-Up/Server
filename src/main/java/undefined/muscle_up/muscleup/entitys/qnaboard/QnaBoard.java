package undefined.muscle_up.muscleup.entitys.qnaboard;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    private Integer userId;

    private Integer view;

    private Integer likeCount;

    private LocalDateTime createdAt;

    public QnaBoard view() {
        view++;
        return this;
    }

    public QnaBoard like() {
        likeCount++;
        return this;
    }

    public QnaBoard unLike() {
        if(likeCount >= 1) likeCount--;
        return this;
    }

    public QnaBoard updateBoard(String title, String content) {
        this.title = title;
        this.content = content;

        return this;
    }
}
