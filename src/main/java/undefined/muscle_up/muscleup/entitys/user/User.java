package undefined.muscle_up.muscleup.entitys.user;

import lombok.*;
import undefined.muscle_up.muscleup.entitys.image.MasterImage;
import undefined.muscle_up.muscleup.entitys.image.UserImage;
import undefined.muscle_up.muscleup.entitys.user.enums.Sex;
import undefined.muscle_up.muscleup.entitys.user.enums.UserType;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private Integer age;

    private Integer height;

    private Integer weight;

    private String introduction;

    private String certificateName;

    private LocalDate acquisitionDate;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private MasterImage masterImage;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserImage userImage;

    @Enumerated(EnumType.STRING)
    private UserType type;

    public User update(String introduction, UserType userType, String certificateName, LocalDate acquisitionDate) {
        this.introduction = introduction;
        this.type = userType;
        this.certificateName = certificateName;
        this.acquisitionDate = acquisitionDate;

        return this;
    }

    public User typeUpdate() {
        this.type = UserType.USER;

        return this;
    }

}
