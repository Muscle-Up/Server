package undefined.muscle_up.muscleup.entitys.image;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private Integer userId;

    private String imageName;

    public UserImage update(String imageName) {
        this.imageName = imageName;

        return this;
    }

}
