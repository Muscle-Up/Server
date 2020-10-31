package undefined.muscle_up.muscleup.entitys.body.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.body.Body;

import java.util.List;
import java.util.Optional;

@Repository
public interface BodyRepository extends CrudRepository<Body, Integer> {
    Optional<Body> findByUserId(Integer userId);
    Optional<Body> findById(Integer id);
    void deleteById(Integer id);
    List<Body> findAllByUserId(Integer userID);
}

