package undefined.muscle_up.muscleup.entitys.message.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.message.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
}
