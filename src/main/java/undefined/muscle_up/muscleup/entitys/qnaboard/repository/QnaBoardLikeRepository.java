package undefined.muscle_up.muscleup.entitys.qnaboard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.qnaboard.QnaBoardLike;

@Repository
public interface QnaBoardLikeRepository extends CrudRepository<QnaBoardLike, Integer> {
}
