package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class SenderNotFoundException extends BusinessException {
    public SenderNotFoundException() {
        super(ErrorCode.SENDER_NOT_FOUND);
    }
}
