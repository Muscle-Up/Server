package undefined.muscle_up.muscleup.entitys.graph.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import undefined.muscle_up.muscleup.entitys.graph.Graph;

import java.util.List;

@Repository
public interface GraphRepository  extends CrudRepository<Graph, Integer> {
    List<Graph> findAllByUserId(Integer userId);
}

