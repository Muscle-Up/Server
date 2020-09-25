package undefined.muscle_up.muscleup.entitys.image.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.image.QnaImage;

@Repository
public interface QnaImageRepository extends CrudRepository<QnaImage, Integer> {
}
