package undefined.muscle_up.muscleup.entitys.message.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.message.Message;
import undefined.muscle_up.muscleup.entitys.message.MessageRoom;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    List<Message> findBySenderIdAndRoomId(Integer senderId, String roodId);
    Optional<Message> findTopByRoomIdOrderByIdDesc(String roomId);

}
