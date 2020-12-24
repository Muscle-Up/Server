package undefined.muscle_up.muscleup.service.message_room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.image.MasterImage;
import undefined.muscle_up.muscleup.entitys.image.repository.MasterImageRepository;
import undefined.muscle_up.muscleup.entitys.message.Message;
import undefined.muscle_up.muscleup.entitys.message.MessageRoom;
import undefined.muscle_up.muscleup.entitys.message.repository.MessageRepository;
import undefined.muscle_up.muscleup.entitys.message.repository.MessageRoomRepository;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.exceptions.ExpertImageNotFoundException;
import undefined.muscle_up.muscleup.exceptions.TargetNotFoundException;
import undefined.muscle_up.muscleup.exceptions.UserNotFoundException;
import undefined.muscle_up.muscleup.payload.response.MessageRoomListResponse;
import undefined.muscle_up.muscleup.security.auth.AuthenticationFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageRoomServiceImpl implements MessageRoomService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MasterImageRepository masterImageRepository;
    private final MessageRoomRepository messageRoomRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public void createMessageRoom(Integer targetId) {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(UserNotFoundException::new);

        User target = userRepository.findById(targetId)
                .orElseThrow(TargetNotFoundException::new);

        String roomId = UUID.randomUUID().toString();
        messageRoomRepository.save(
                MessageRoom.builder()
                    .roomId(roomId)
                    .userId(user.getId())
                    .build()
        );
        messageRoomRepository.save(
                MessageRoom.builder()
                        .roomId(roomId)
                        .userId(target.getId())
                        .build()
        );
    }

    @Override
    public List<MessageRoomListResponse> getMessageRoomList() {
        User user = userRepository.findById(authenticationFacade.getId())
                .orElseThrow(RuntimeException::new);

        List<MessageRoom> rooms = messageRoomRepository.findRoomsForTarget(user.getId());
        List<MessageRoomListResponse> roomListResponses = new ArrayList<>();

        for (MessageRoom messageRoom : rooms) {
            User target = userRepository.findById(messageRoom.getUserId())
                    .orElseThrow(TargetNotFoundException::new);

            MasterImage masterImage = masterImageRepository.findByUserId(messageRoom.getUserId())
                    .orElseThrow(ExpertImageNotFoundException::new);

            Optional<Message> message = messageRepository.findTopByRoomIdOrderByIdDesc(messageRoom.getRoomId());
            Integer senderId = message.map(Message::getSenderId).orElseThrow(UserNotFoundException::new);
            String lastMessage = message.map(Message::getContent).orElse("");
            boolean isRead;
            if(user.getId().equals(senderId)) {
                isRead = true;
            }else {
                isRead = message.map(Message::isRead).orElse(false);
            }
            roomListResponses.add(
                    MessageRoomListResponse.builder()
                            .userId(target.getId())
                            .userName(target.getName())
                            .userImage(masterImage.getImageName())
                            .lastMessage(lastMessage)
                            .isRead(isRead)
                            .build()
            );
         }

        return roomListResponses;
    }
}
