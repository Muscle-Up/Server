package undefined.muscle_up.muscleup.exceptions;

import undefined.muscle_up.muscleup.error.excepion.BusinessException;
import undefined.muscle_up.muscleup.error.excepion.ErrorCode;

public class GraphNotFoundException extends BusinessException {
    public GraphNotFoundException() {
        super(ErrorCode.GRAPH_NOT_FOUND);
    }
}
