package undefined.muscle_up.muscleup.entitys.qnaboard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.qnaboard.QnaBoardView;

import java.util.Optional;

@Repository
public interface QnaBoardViewRepository extends CrudRepository<QnaBoardView, Integer> {

    Optional<QnaBoardView> findByBoardIdAndUserId(Integer boardId, Integer userId);
}
