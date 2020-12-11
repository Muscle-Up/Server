package undefined.muscle_up.muscleup.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.refresh_token.RefreshToken;
import undefined.muscle_up.muscleup.entitys.refresh_token.reposiroty.RefreshTokenRepository;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;
import undefined.muscle_up.muscleup.payload.request.AccountRequest;
import undefined.muscle_up.muscleup.payload.response.TokenResponse;
import undefined.muscle_up.muscleup.security.JwtProvider;
import undefined.muscle_up.muscleup.service.refresh_token.RefreshTokenServiceImpl;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${auth.jwt.exp.refresh}")
    private Long refreshExp;

    @Value("${auth.jwt.prefix}")
    private String tokenType;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenServiceImpl refreshTokenService;

    @Override
    public TokenResponse signIn(AccountRequest accountRequest) {
        return userRepository.findByEmail(accountRequest.getEmail())
                .filter(user -> passwordEncoder.matches(accountRequest.getPassword(), user.getPassword()))
                .map(User::getId)
                .map(id -> {
                    String accessToken = jwtProvider.generateAccessToken(id);
                    String refreshToken = jwtProvider.generateRefreshToken(id);
                    refreshTokenService.save(new RefreshToken(id, refreshToken, refreshExp));
                    return new TokenResponse(accessToken, refreshToken, tokenType);
                })
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public TokenResponse refreshToken(String receivedToken) {
        if (!jwtProvider.isRefreshToken(receivedToken))
            throw new RuntimeException();

        return refreshTokenRepository.findByRefreshToken(receivedToken)
                .map(refreshToken -> {
                    String generatedRefreshToken = jwtProvider.generateRefreshToken(refreshToken.getId());
                    return refreshToken.update(generatedRefreshToken, refreshExp);
                })
                .map(refreshTokenRepository::save)
                .map(refreshToken -> {
                    String generatedAccessToken = jwtProvider.generateAccessToken(refreshToken.getId());
                    return new TokenResponse(generatedAccessToken, refreshToken.getRefreshToken(), tokenType);
                })
                .orElseThrow(RuntimeException::new);
    }
}
