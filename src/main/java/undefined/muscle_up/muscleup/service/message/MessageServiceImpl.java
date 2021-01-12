package undefined.muscle_up.muscleup.service.message;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.message.Message;
import undefined.muscle_up.muscleup.entitys.message.repository.MessageRepository;
import undefined.muscle_up.muscleup.entitys.message.repository.MessageRoomRepository;
import undefined.muscle_up.muscleup.entitys.message.type.MessageType;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.exceptions.MessageRoomNotFoundException;
import undefined.muscle_up.muscleup.exceptions.SenderNotFoundException;
import undefined.muscle_up.muscleup.exceptions.UserNotFoundException;
import undefined.muscle_up.muscleup.payload.response.MessageResponse;
import undefined.muscle_up.muscleup.security.auth.AuthenticationFacade;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MessageRoomRepository messageRoomRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<MessageResponse> getMessageList(String roomId) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        List<Message> messageList = messageRepository.findBySenderIdAndRoomId(user.getId(), roomId);

        List<MessageResponse> messageResponse = new ArrayList<>();
        for (Message message : messageList){
            User sender = userRepository.findById(message.getSenderId())
                    .orElseThrow(SenderNotFoundException::new);

            messageResponse.add(
                    MessageResponse.builder()
                        .userId(message.getSenderId())
                        .userName(sender.getName())
                        .message(message.getContent())
                        .messageType(message.getMessageType())
                        .isMine(user.getId().equals(sender.getId()))
                        .build()
            );
        }

        return messageResponse;
    }

    @SneakyThrows
    @Override
    public void sendMessage(User user, String roomId, String content, MessageType messageType) {
        boolean messageRoom = messageRoomRepository.existsByRoomIdAndUserId(roomId, user.getId());
        if(!messageRoom) throw new MessageRoomNotFoundException();

        messageRepository.save(
                Message.builder()
                    .messageType(messageType)
                    .content(content)
                    .senderId(user.getId())
                    .roomId(roomId)
                    .isRead(false)
                    .build()
        );
    }
}
