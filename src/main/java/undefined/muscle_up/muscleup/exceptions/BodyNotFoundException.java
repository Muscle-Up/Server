package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class BodyNotFoundException extends BusinessException {
    public BodyNotFoundException() {
        super(ErrorCode.BODY_NOT_FOUND);
    }
}
