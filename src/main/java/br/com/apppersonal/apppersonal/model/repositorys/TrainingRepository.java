package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.TrainingEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingEntity, Long> {
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM TrainingEntity t WHERE t.id = ?1 AND t.deleted = false")
    Boolean isDeletedTraining(Long id);

}
