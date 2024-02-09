package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    @Query("select u from UserEntity u where u.id = ?1 and u.deleted = false")
    UserEntity findByIdAndNotDeleted(Long id);

    UserEntity findByIdAndRole(Long id, Role role);
}
