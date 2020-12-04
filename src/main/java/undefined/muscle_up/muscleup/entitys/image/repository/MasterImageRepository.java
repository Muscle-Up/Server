package undefined.muscle_up.muscleup.entitys.image.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.image.MasterImage;

import java.util.Optional;

@Repository
public interface MasterImageRepository extends CrudRepository<MasterImage, Integer> {
    Optional<MasterImage> deleteByUserId(Integer userId);
    Optional<MasterImage> findByUserId(Integer userId);
}
