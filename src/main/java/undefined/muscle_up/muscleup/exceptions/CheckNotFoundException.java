package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class CheckNotFoundException extends BusinessException {
    public CheckNotFoundException() {
        super(ErrorCode.CHECK_NOT_FOUND);
    }
}
