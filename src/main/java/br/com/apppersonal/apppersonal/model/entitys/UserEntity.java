package br.com.apppersonal.apppersonal.model.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String role = "ROLE_USER";

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private ProfileEntity profile;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserGaleryEntity> userGaleryEntity;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private UserMetricsEntity userMetricsEntity;

    public UserEntity(Long id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserEntity() {

    }
}
