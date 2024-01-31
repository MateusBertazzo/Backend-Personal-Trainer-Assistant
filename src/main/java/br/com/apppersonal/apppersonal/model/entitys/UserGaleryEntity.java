package br.com.apppersonal.apppersonal.model.entitys;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "user_galery")
public class UserGaleryEntity extends BaseEntity{

    @Column(name = "url_foto")
    private String urlFoto;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "data_foto")
    private LocalDate dataFoto;

    public UserGaleryEntity(String urlFoto, UserEntity user, LocalDate dataFoto) {
        this.urlFoto = urlFoto;
        this.user = user;
        this.dataFoto = dataFoto;
    }
    public UserGaleryEntity() {}

    public UserGaleryEntity(UserEntity userEntity) { this.user = userEntity; }
}
