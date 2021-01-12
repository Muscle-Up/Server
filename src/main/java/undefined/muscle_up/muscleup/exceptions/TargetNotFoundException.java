package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class TargetNotFoundException extends BusinessException {
    public TargetNotFoundException() {
        super(ErrorCode.TARGET_NOT_FOUND);
    }
}
