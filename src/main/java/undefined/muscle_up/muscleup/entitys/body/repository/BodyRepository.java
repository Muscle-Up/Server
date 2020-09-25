package undefined.muscle_up.muscleup.entitys.body.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyRepository extends CrudRepository<BodyRepository, Integer> {
}
