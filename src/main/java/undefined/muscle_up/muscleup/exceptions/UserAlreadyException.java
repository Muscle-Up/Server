package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class UserAlreadyException extends BusinessException {
    public UserAlreadyException() {
        super(ErrorCode.USER_ALREADY_EXCEPTION);
    }
}
