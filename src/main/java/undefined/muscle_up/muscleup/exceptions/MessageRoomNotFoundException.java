package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class MessageRoomNotFoundException extends BusinessException {
    public MessageRoomNotFoundException() {
        super(ErrorCode.MESSAGE_ROOM_NOT_FOUND);
    }
}
