package undefined.muscle_up.muscleup.entitys.graph;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Graph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    @Column(unique = true)
    private LocalDate createAt;

    private double weight;

    private double muscleMass;

    private double bodyFatMass;
}

