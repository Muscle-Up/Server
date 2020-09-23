package undefined.muscle_up.muscleup.entitys.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.user.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
