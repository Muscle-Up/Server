package undefined.muscle_up.muscleup.entitys.qnaboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.qnaboard.QnaBoard;

import java.util.List;
import java.util.Optional;

@Repository
public interface QnaBoardRepository extends JpaRepository<QnaBoard, Integer> {
    Optional<QnaBoard> findById(Integer boardId);
    void deleteById(Integer boardId);

    @Query(value = "select qna.* from qna_board as qna join ban_user as ban on ban.blocker_people = :userId where qna.user_id != ban.block_out_people limit :startIndex, :endIndex", nativeQuery = true)
    List<QnaBoard> findAllBoardsWithoutBlockOut(Integer userId, Integer startIndex, Integer endIndex);
}
