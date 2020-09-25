package undefined.muscle_up.muscleup.entitys.body.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.body.Body;

@Repository
public interface BodyRepository extends CrudRepository<Body, Integer> {
}
