package undefined.muscle_up.muscleup.security.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Authentication getAuthentication(){

        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Integer getId(){
        Authentication auth = this.getAuthentication();
        if(auth.getPrincipal() instanceof  AuthDetails){
            return ((AuthDetails) auth.getPrincipal()).getUser().getId();
        }else {
            return Integer.parseInt(this.getAuthentication().getName());
        }
    }

}
