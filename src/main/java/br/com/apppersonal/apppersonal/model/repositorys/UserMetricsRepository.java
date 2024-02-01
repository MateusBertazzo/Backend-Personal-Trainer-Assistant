package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserMetricsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserMetricsRepository extends JpaRepository<UserMetricsEntity, Long> {
    @Query("select p from UserMetricsEntity p where p.user.id = ?1 and p.deleted = false")
    UserMetricsEntity findByUserId(Long userId);
}
