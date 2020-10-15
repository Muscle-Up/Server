package undefined.muscle_up.muscleup.entitys.comment;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    private LocalDateTime createdAt;

    private Integer boardId;

    private Integer userId;

    public Comment updateContent(String content) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
        return this;
    }
}
