package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;
import undefined.muscle_up.muscleup.entitys.message.type.MessageType;

@Getter
@Builder
public class MessageResponse {
    private Integer userId;
    private String userName;
    private String message;
    private boolean isMine;
    private MessageType messageType;
}
