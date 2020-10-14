package undefined.muscle_up.muscleup.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PageResponse {
    private Integer totalElements;
    private Integer totalPages;
    private List<ExpertResponse> expertListResponse;
}
