package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {

    @Query("select e from ExerciseEntity e where e.training.id = ?1 and e.deleted = false")
    List<ExerciseEntity> findAllByTrainingId(Long trainingId);
}
