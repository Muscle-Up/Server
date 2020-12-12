package undefined.muscle_up.muscleup.payload.request;

import lombok.Getter;
import undefined.muscle_up.muscleup.entitys.message.type.MessageType;

@Getter
public class MessageRequest {
    private String roomId;
    private String message;
    private MessageType messageType;
}
