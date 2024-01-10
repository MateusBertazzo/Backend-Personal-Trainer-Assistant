package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.TrainingEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<TrainingEntity, Long> {
    List<TrainingEntity> findAllByUser(UserEntity user);
}
