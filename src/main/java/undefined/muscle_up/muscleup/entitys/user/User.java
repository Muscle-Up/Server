package undefined.muscle_up.muscleup.entitys.user;

import lombok.*;
import undefined.muscle_up.muscleup.entitys.user.enums.Sex;
import undefined.muscle_up.muscleup.entitys.user.enums.UserType;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    private UserType type;

    public User update(String introduction, UserType userType) {
        this.introduction = introduction;
        this.type = userType;

        return this;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setHeight(Integer height){
        this.height = height;
    }

    public void setWeight(Integer weight){
        this.weight = weight;
    }
}
