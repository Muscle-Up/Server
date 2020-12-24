package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class ImageNotFoundException extends BusinessException {
    public ImageNotFoundException() {
        super(ErrorCode.IMAGE_NOT_FOUND);
    }
}
