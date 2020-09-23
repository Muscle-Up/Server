package undefined.muscle_up.muscleup.entitys.qnaboard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.qnaboard.QnaBoard;

@Repository
public interface QnaBoardRepository extends CrudRepository<QnaBoard, Integer> {
}
