package undefined.muscle_up.muscleup.service.user;

import undefined.muscle_up.muscleup.payload.response.MainPageResponse;
import undefined.muscle_up.muscleup.payload.request.SignUpRequest;
import undefined.muscle_up.muscleup.payload.request.UpdateRequest;

public interface UserService {
    void signUp(SignUpRequest signUpRequest);
    void changePw(String password);
    void updateUser(UpdateRequest updateRequest);
    MainPageResponse mainPage();
}
