package undefined.muscle_up.muscleup.service.message_room;

import undefined.muscle_up.muscleup.payload.response.MessageRoomListResponse;

import java.util.List;

public interface MessageRoomService {
    void createMessageRoom(Integer targetId);
    List<MessageRoomListResponse> getMessageRoomList();
}
