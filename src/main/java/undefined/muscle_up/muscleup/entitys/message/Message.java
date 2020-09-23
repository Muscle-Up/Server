package undefined.muscle_up.muscleup.entitys.message;

import lombok.*;
import undefined.muscle_up.muscleup.entitys.user.enums.UserType;

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
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer message;

    private LocalDateTime time;

    private UserType type;

    private String room_seq;

}
