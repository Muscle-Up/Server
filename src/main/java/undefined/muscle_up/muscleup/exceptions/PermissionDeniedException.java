package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class PermissionDeniedException extends BusinessException {
    public PermissionDeniedException() {
        super(ErrorCode.PERMISSION_DENIED_EXCEPTION);
    }

}
