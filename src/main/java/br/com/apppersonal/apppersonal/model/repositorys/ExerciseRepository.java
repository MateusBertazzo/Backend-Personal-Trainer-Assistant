package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {
}
