package undefined.muscle_up.muscleup.service.socket;

import com.corundumstudio.socketio.SocketIOClient;
import undefined.muscle_up.muscleup.payload.request.MessageRequest;

public interface SocketService {
    void connect(SocketIOClient client);
    void disConnect(SocketIOClient client);
    void joinRoom(SocketIOClient client, String room);
    void chat(SocketIOClient client, MessageRequest messageRequest);
}
