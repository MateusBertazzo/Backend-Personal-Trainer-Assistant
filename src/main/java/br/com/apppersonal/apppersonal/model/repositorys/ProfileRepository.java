package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    @Query("select p from ProfileEntity p where p.deleted = false")
    List<ProfileEntity> findAllNotDeleted();

    @Query("select p from ProfileEntity p where p.user.id = ?1 and p.deleted = false")
    ProfileEntity findByIdAndNotDeleted(Long id);
}
