package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.UserMetricsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMetricsRepository extends JpaRepository<UserMetricsEntity, Long> {
}
