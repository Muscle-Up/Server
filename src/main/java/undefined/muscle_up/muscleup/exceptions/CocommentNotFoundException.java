package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class CocommentNotFoundException extends BusinessException {
    public CocommentNotFoundException() {
        super(ErrorCode.COCOMENT_NOT_FOUND);
    }
}
