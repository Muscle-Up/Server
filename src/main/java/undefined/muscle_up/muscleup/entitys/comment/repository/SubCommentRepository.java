package undefined.muscle_up.muscleup.entitys.comment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.comment.SubComment;

import java.util.List;

@Repository
public interface SubCommentRepository extends CrudRepository<SubComment, Integer> {
    List<SubComment> findAllByCommentIdOrderByIdAsc(Integer subCommentId);

    void deleteAllByCommentId(Integer commentId);

    void deleteById(Integer subCommentId);
}
