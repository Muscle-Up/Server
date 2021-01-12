package undefined.muscle_up.muscleup.entitys.qnaboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.qnaboard.QnaBoard;

import java.util.List;
import java.util.Optional;

@Repository
public interface QnaBoardRepository extends JpaRepository<QnaBoard, Integer> {
    Optional<QnaBoard> findById(Integer boardId);
    List<QnaBoard> findAllByOrderByCreatedAtDesc();
    void deleteById(Integer boardId);
}
