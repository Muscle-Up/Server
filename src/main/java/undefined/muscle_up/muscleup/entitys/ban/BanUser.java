package undefined.muscle_up.muscleup.entitys.ban;

import lombok.*;
import undefined.muscle_up.muscleup.entitys.qnaboard.QnaBoard;

import javax.persistence.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BanUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer blockerPeople;

    private Integer blockOutPeople;

}
