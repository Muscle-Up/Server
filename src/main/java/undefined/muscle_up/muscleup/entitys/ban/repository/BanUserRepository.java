package undefined.muscle_up.muscleup.entitys.ban.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.ban.BanUser;

@Repository
public interface BanUserRepository extends CrudRepository<BanUser, Integer> {
}
