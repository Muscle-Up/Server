package undefined.muscle_up.muscleup.service.expert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.payload.request.ExpertRegistrationRequest;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService{

    private final UserRepository userRepository;

    @Override
    public void registration(ExpertRegistrationRequest expertRegistration) {

    }
}
