package undefined.muscle_up.muscleup.entitys.image.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.image.BodyImage;

import java.util.List;
import java.util.Optional;

@Repository
public interface BodyImageRepository extends CrudRepository<BodyImage, Integer> {
    Optional<BodyImage> findByBodyId(Integer bodyId);
    Optional<BodyImage> findByImageName(String imageName);
    void deleteById(Integer id);
}
