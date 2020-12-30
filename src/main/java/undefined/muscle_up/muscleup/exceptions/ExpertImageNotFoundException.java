package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class ExpertImageNotFoundException extends BusinessException {
    public ExpertImageNotFoundException() {
        super(ErrorCode.EXPERT_IMAGE_NOT_FOUND);
    }
}
