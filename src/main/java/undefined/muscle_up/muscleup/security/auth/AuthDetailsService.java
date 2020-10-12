package undefined.muscle_up.muscleup.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import undefined.muscle_up.muscleup.entitys.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public AuthDetails loadUserByUsername(String receiptCode) throws UsernameNotFoundException {
        return userRepository.findById(Integer.parseInt(receiptCode))
                .map(AuthDetails::new)
                .orElseThrow(RuntimeException::new);
    }

}
