package br.com.apppersonal.apppersonal.model.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "profiles")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String foto;

    @Column(name = "numero_telefone", unique = true)
    private String numeroTelefone;

    private String observacao;

    private String objetivo;

    public ProfileEntity(Long id, UserEntity user, String foto, String numeroTelefone, String observacao, String objetivo) {
        this.id = id;
        this.user = user;
        this.foto = foto;
        this.numeroTelefone = numeroTelefone;
        this.observacao = observacao;
        this.objetivo = objetivo;
    }

    public ProfileEntity() {}

    public ProfileEntity(UserEntity userEntity) {
        this.user = userEntity;
    }
}
