package undefined.muscle_up.muscleup.entitys.refresh_token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "refresh_token")
@AllArgsConstructor
public class RefreshToken {

    @Id
    private Integer id;

    @Indexed
    private String refreshToken;

    @Indexed
    private Long ttl;

    public RefreshToken update(String refreshToken, Long ttl) {
        this.refreshToken = refreshToken;
        this.ttl = ttl;
        return this;
    }

}
