package undefined.muscle_up.muscleup.service.search;

import undefined.muscle_up.muscleup.payload.response.ExpertResponse;

import java.util.List;

public interface SearchService {
    List<ExpertResponse> searchExpert(String certificateName);
}
