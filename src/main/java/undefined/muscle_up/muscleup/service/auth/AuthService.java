package undefined.muscle_up.muscleup.service.auth;

import undefined.muscle_up.muscleup.payload.request.AccountRequest;
import undefined.muscle_up.muscleup.payload.response.TokenResponse;

public interface AuthService {
    TokenResponse signIn(AccountRequest accountRequest);
    TokenResponse refreshToken(String refreshToken);
}

