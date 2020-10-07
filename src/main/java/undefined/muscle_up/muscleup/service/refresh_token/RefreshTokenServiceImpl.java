package undefined.muscle_up.muscleup.service.refresh_token;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.refresh_token.RefreshToken;
import undefined.muscle_up.muscleup.entitys.refresh_token.reposiroty.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{

    private final RefreshTokenRepository refreshTokenRepository;

    @Async
    @Override
    public void save(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }
}
