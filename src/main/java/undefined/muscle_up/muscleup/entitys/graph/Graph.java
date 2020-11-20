package undefined.muscle_up.muscleup.entitys.graph;

import lombok.*;
import undefined.muscle_up.muscleup.entitys.graph.enums.GraphType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    private double weight;

    private double muscleMass;

    private double bodyFatMass;
}
