package undefined.muscle_up.muscleup.entitys.comment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<CommentRepository, Integer> {
}
