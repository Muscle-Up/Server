package undefined.muscle_up.muscleup.entitys.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.user.User;
import undefined.muscle_up.muscleup.entitys.user.enums.UserType;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findAllByEmail(String email);
    Page<User> findAllByType(UserType userType, Pageable page);
}
