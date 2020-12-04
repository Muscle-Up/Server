package undefined.muscle_up.muscleup.service.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.message.repository.MessageRepository;
import undefined.muscle_up.muscleup.entitys.message.repository.MessageRoomRepository;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.payload.request.MessageRequest;
import undefined.muscle_up.muscleup.payload.response.ErrorResponse;
import undefined.muscle_up.muscleup.payload.response.MessageResponse;
import undefined.muscle_up.muscleup.security.JwtProvider;
import undefined.muscle_up.muscleup.service.message.MessageService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SocketServiceImpl implements SocketService {

    private final SocketIOServer socketIOServer;

    private final JwtProvider jwtTokenProvider;

    private final MessageService messageService;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MessageRoomRepository messageRoomRepository;

    @Override
    public void connect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("token");
        if (!jwtTokenProvider.validateToken(token)) {
            clientDisconnect(client, 403, "Can not resolve token");
            return;
        }

        User user;
        try {
            user = userRepository.findById(Integer.valueOf(jwtTokenProvider.getUserId(token)))
                    .orElseThrow(RuntimeException::new);
            client.set("user", user);
        } catch (Exception e) {
            clientDisconnect(client, 404, "Could not get user");
            return;
        }
    }

    @Override
    public void disConnect(SocketIOClient client) {
        printLog(
                client,
                String.format("Socket Disconnected, Session Id: %s%n", client.getSessionId())
        );
    }

    @Override
    public void joinRoom(SocketIOClient client, String roomId) {
        User user = client.get("user");
        Integer targetId;
        if(user == null){
            clientDisconnect(client, 403, "Invalid Connection");
            return;
        }

        boolean existUser = messageRoomRepository.existsByRoomIdAndUserId(roomId, user.getId());
        if(!existUser) {
            clientDisconnect(client, 401, "Invalid Room");
            return;
        }

        try {
            targetId = messageRoomRepository.findByRoomIdAndUserIdNot(roomId, user.getId())
                    .orElseThrow(RuntimeException::new).getUserId();
        }catch (Exception e){
            clientDisconnect(client, 404, "Target Not Found");
            return;
        }

        printLog(
                client,
                String.format("Join Room [senderId(%d) -> receiverId(%d)] Session Id: %s%n",
                        user.getId(), targetId, client.getSessionId())
        );
    }

    @Override
    public void chat(SocketIOClient client, MessageRequest messageRequest) {
        if(!client.getAllRooms().contains(messageRequest.getRoomId())){
            clientDisconnect(client, 401, "Invalid Connection");
            return;
        }

        User user = client.get("user");
        messageService.sendMessage(
                user,
                messageRequest.getRoomId(),
                messageRequest.getMessage(),
                messageRequest.getMessageType()
        );

        socketIOServer.getRoomOperations(messageRequest.getRoomId()).sendEvent(
                "send",
                client,
                MessageResponse.builder()
                    .userId(user.getId())
                    .userName(user.getName())
                    .message(messageRequest.getMessage())
                    .messageType(messageRequest.getMessageType())
                    .isMine(false)
                    .build()
        );
    }

    private void printLog(SocketIOClient client, String content){
        String stringDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        System.out.printf(
                "%s  %s - [%s] - %s",
                stringDate,
                "SOCKET",
                client.getRemoteAddress().toString().substring(1),
                content
        );
    }

    private void clientDisconnect(SocketIOClient client, Integer status, String reason){
        client.sendEvent("error", new ErrorResponse(status, reason));
        client.disconnect();
        printLog(
                client,
                String.format("Socket Disconnected, Session Id: %s - %s%n", client.getSessionId(), reason)
        );
    }
}
