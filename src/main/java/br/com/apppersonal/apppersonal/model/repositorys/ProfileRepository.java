package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    ProfileEntity findByUserId(Long id);

    @Query("select p from ProfileEntity p where p.deleted = false")
    List<ProfileEntity> findAllNotDeleted();

    @Query("select p from ProfileEntity p where p.id = ?1 and p.deleted = false")
    ProfileEntity findByIdAndNotDeleted(Long id);
}
