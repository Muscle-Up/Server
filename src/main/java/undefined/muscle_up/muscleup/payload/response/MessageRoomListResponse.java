package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageRoomListResponse {
    private Integer userId;
    private String userName;
    private String userImage;
    private String lastMessage;
    private boolean isRead;
}
