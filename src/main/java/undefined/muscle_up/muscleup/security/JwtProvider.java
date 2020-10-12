package undefined.muscle_up.muscleup.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import undefined.muscle_up.muscleup.security.auth.AuthDetails;
import undefined.muscle_up.muscleup.security.auth.AuthDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Value("${auth.jwt.exp.access}")
    private Long accessTokenExpiration;

    @Value("${auth.jwt.exp.refresh}")
    private Long refreshTokenExpiration;

    @Value("${auth.jwt.header}")
    private String header;

    @Value("${auth.jwt.prefix}")
    private String prefix;

    private AuthDetailsService authDetailsService;

    public String generateAccessToken(Integer id){
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration * 1000))
                .setSubject(id.toString())
                .claim("type", "access_token")
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshToken(Integer id){
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000))
                .setSubject(id.toString())
                .claim("type", "refresh_token")
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(header);
        if(bearerToken != null && bearerToken.startsWith(prefix)){
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody().getSubject();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public Authentication getAuthentication(String token){
        AuthDetails authDetails = authDetailsService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(authDetails, "", authDetails.getAuthorities());
    }

    public String getReceiptCode(String token){
        try{
            return Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    public String getUserName(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isRefreshToken(String token){
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().get("type").equals("refresh_token");
    }
}