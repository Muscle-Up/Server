package undefined.muscle_up.muscleup.entitys.image.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.image.MasterImage;
import undefined.muscle_up.muscleup.entitys.image.UserImage;

import java.util.Optional;

@Repository
public interface UserImageRepository extends CrudRepository<UserImage, Integer> {
    Optional<UserImage> findByUserId(Integer userId);
}
