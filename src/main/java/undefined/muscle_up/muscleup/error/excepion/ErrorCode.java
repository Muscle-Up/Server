package undefined.muscle_up.muscleup.error.excepion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  ErrorCode {

    INVALID_TOKEN(401,"Invalid Token"),
    EXPIRED_TOKEN(401,"Expired Token"),
    PERMISSION_DENIED_EXCEPTION(401,"Permission Denied"),
    USER_NOT_FOUND(404,"User Not Found"),
    BODY_NOT_FOUND(404, "Body Not Found"),
    FILE_NOT_FOUND(404, "File Not Found"),
    BOARD_NOT_FOUND(404, "Board Not Found"),
    CHECK_NOT_FOUND(404, "Check Not Found"),
    GRAPH_NOT_FOUND(404, "Graph Not Found"),
    TARGET_NOT_FOUND(404, "Target Not Found"),
    WRITER_NOT_FOUND(404, "Writer Not Found"),
    SENDER_NOT_FOUND(404, "Sender Not Found"),
    COMMENT_NOT_FOUND(404, "Comment Not Found"),
    COCOMENT_NOT_FOUND(404, "Cocommnet Not Found"),
    BODY_IMAGE_NOT_FOUND(404, "Body Image Not Found"),
    MESSAGE_ROOM_NOT_FOUND(404, "Message Room Not Found"),
    EXPERT_IMAGE_NOT_FOUND(404, "Expert Image Not Found"),
    USER_ALREADY_EXCEPTION(409, "User Already Exception"),
    USER_ALREADY_REGISTERED(409, "User Already Registered");

    private final int status;
    private final String message;

}
