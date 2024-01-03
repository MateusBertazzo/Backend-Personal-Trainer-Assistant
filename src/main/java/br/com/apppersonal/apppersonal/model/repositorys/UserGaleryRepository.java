package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.UserGaleryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGaleryRepository extends JpaRepository<UserGaleryEntity, Long> {
    List<UserGaleryEntity> findAllByUserId(Long userId);
}
