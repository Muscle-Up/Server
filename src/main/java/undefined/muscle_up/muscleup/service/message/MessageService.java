package undefined.muscle_up.muscleup.service.message;

import undefined.muscle_up.muscleup.entitys.message.type.MessageType;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.payload.response.MessageResponse;

import java.util.List;

public interface MessageService {
    List<MessageResponse> getMessageList(String roomId);
    void sendMessage(User user, String roomId, String content, MessageType messageType);
}
