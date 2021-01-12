package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class WriterNotFoundException extends BusinessException {
    public WriterNotFoundException() {
        super(ErrorCode.WRITER_NOT_FOUND);
    }
}
