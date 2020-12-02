package undefined.muscle_up.muscleup.entitys.image;

import lombok.*;
import undefined.muscle_up.muscleup.entitys.user.User;

import javax.persistence.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MasterImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imageName;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public MasterImage update(String imageName) {
        this.imageName = imageName;

        return this;
    }

}
