package undefined.muscle_up.muscleup.entitys.message.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.message.MessageRoom;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRoomRepository extends CrudRepository<MessageRoom, Integer> {
    boolean existsByRoomIdAndUserId(String roomId, Integer userId);
    Optional<MessageRoom> findByRoomIdAndUserIdNot(String roomId, Integer userId);

    @Query(value = "select target_room.id, target_room.room_id, target_room.user_id from room as my_room join room as target_room join message as m on target_room.room_id=my_room.room_id where m.room_id=target_room.room_id and target_room.user_id != my_room.user_id and my_room.user_id = ?1 group by user_id order by is_read", nativeQuery = true)
    List<MessageRoom> findRoomsForTarget(Integer userId);

}
