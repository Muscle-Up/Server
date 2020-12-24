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
    IMAGE_NOT_FOUND(404, "Image Not Found"),
    USER_ALREADY_EXCEPTION(409, "User Already Exception");

    private final int status;
    private final String message;

}
