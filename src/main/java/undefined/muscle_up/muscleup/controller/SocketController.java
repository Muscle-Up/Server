package undefined.muscle_up.muscleup.controller;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import undefined.muscle_up.muscleup.payload.request.MessageRequest;
import undefined.muscle_up.muscleup.service.socket.SocketService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class SocketController {

    private final SocketIOServer server;

    private final SocketService socketService;

    @PostConstruct
    public void setSocketMapping() {
        server.addConnectListener(socketService::connect);
        server.addDisconnectListener(socketService::disConnect);

        server.addEventListener("joinRoom", String.class,
                (client, room, ackSender) -> socketService.joinRoom(client, room));

        server.addEventListener("send", MessageRequest.class,
                (client, data, ackSender) -> socketService.chat(client, data));
    }
}
