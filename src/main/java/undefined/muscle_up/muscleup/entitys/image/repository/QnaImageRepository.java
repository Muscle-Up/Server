package undefined.muscle_up.muscleup.entitys.image.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.image.QnaImage;

import java.util.List;

@Repository
public interface QnaImageRepository extends CrudRepository<QnaImage, Integer> {

    List<QnaImage> findByBoardIdOrderById(Integer boardId);
    void deleteByBoardId(Integer boardId);
}
