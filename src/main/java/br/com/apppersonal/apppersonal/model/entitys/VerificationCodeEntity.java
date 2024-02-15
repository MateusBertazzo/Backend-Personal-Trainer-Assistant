package br.com.apppersonal.apppersonal.model.entitys;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "verification_codes")
public class VerificationCodeEntity extends BaseEntity{

    @Column(length = 1000)
    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public VerificationCodeEntity(String code, UserEntity user) {
        this.code = code;
        this.user = user;
    }

    public VerificationCodeEntity() {
    }

    public VerificationCodeEntity(UserEntity user) {
        this.user = user;
    }
}
