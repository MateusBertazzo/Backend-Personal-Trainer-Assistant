package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.TrainingEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingEntity, Long> {
    List<TrainingEntity> findAllByUser(UserEntity user);

//    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS result from TrainingEntity t where t.id = ?1 and t.deleted = false"
//    )
//    Boolean isDeletedTraining(Long id);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM TrainingEntity t WHERE t.id = ?1 AND t.deleted = false")
    Boolean isDeletedTraining(Long id);

}
