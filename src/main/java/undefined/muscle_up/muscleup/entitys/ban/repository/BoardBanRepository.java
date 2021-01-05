package undefined.muscle_up.muscleup.entitys.ban.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.ban.BoardBan;

@Repository
public interface BoardBanRepository extends CrudRepository<BoardBan, Integer> {
}
