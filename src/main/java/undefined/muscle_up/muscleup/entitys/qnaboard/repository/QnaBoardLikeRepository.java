package undefined.muscle_up.muscleup.entitys.qnaboard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.qnaboard.QnaBoardLike;

import java.util.Optional;

@Repository
public interface QnaBoardLikeRepository extends CrudRepository<QnaBoardLike, Integer> {

    Optional<QnaBoardLike> findByBoardIdAndUserId(Integer boardId, Integer userId);

    void deleteByBoardId(Integer boardId);
}
