package undefined.muscle_up.muscleup.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
    
}
