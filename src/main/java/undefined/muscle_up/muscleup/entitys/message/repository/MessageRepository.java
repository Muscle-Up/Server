package undefined.muscle_up.muscleup.entitys.message.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<MessageRepository, Integer> {
}
