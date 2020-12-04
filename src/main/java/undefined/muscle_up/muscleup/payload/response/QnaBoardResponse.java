package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QnaBoardResponse {

    private int totalElements;

    private int totalPage;

    private List qnaBoardListResponse;
}
