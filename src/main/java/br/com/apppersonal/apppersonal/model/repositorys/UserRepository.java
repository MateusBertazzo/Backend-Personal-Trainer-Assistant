package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByName(String name);
}
