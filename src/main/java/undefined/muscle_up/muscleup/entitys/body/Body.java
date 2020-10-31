package undefined.muscle_up.muscleup.entitys.body;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Body {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer userId;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDate createdAt;

    public Body update(String title, String content) {
        this.title = title;
        this.content = content;

        return this;
    }
}
