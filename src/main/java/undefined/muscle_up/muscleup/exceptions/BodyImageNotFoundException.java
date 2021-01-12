package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class BodyImageNotFoundException extends BusinessException {
    public BodyImageNotFoundException() {
        super(ErrorCode.BODY_IMAGE_NOT_FOUND);
    }
}
