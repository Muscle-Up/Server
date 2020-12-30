package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class CommnetNotFoundException extends BusinessException {
    public CommnetNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
