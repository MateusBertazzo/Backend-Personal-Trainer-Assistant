package br.com.apppersonal.apppersonal.model.repositorys;

import br.com.apppersonal.apppersonal.model.entitys.VerificationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCodeEntity, Long> {
    /* 
     * Consulta todas os códigos de verificação por usuários e não deletados
     */
    @Query("select v from VerificationCodeEntity v where v.user.id = ?1 and v.deleted = false")
    VerificationCodeEntity findByUserId(Long id);
}
