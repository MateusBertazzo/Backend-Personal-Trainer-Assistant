package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.UserGaleryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGaleryRepository extends JpaRepository<UserGaleryEntity, Long> {
    @Query("select p from UserGaleryEntity p where p.id = ?1 and p.deleted = false")
    List<UserGaleryEntity> findAllByUserId(Long userId);
}
