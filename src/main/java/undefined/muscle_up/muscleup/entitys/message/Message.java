package undefined.muscle_up.muscleup.entitys.message;

import lombok.*;
import undefined.muscle_up.muscleup.entitys.message.type.MessageType;
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

    private MessageType messageType;

    private String content;

    private String roomId;

    private Integer senderId;

    private boolean isRead;

}
