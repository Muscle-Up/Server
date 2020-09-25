package undefined.muscle_up.muscleup.entitys.image.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.image.BodyImage;

@Repository
public interface BodyImageRepository extends CrudRepository<BodyImage, Integer> {
}
