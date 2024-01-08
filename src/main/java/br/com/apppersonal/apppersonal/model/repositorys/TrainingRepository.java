package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<TrainingEntity, Long> {
}
