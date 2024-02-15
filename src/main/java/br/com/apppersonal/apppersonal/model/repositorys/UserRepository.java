package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // busca users por email e que não estejam deletados
    @Query("select u from UserEntity u where u.email = ?1 and u.deleted = false")
    UserEntity findByEmail(String email);

    // busca users por username e que não estejam deletados
    @Query("select u from UserEntity u where u.username = ?1 and u.deleted = false")
    UserEntity findByUsername(String username);

    // busca users por id e que não estejam deletados
    @Query("select u from UserEntity u where u.id = ?1 and u.deleted = false")
    UserEntity findByIdAndNotDeleted(Long id);

    // busca users por id e role
    UserEntity findByIdAndRole(Long id, Role role);

    // busca users por role e que personal trainer id
    List<UserEntity> findByRoleAndPersonalTrainerId(Role role, Long personalTrainerId);
}
