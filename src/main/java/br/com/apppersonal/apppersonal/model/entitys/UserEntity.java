package br.com.apppersonal.apppersonal.model.entitys;

import br.com.apppersonal.apppersonal.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails, GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Role role;

//    @JsonIgnore
//    @OneToOne(mappedBy = "user")
//    private ProfileEntity profile;

//    @JsonIgnore
//    @OneToMany(mappedBy = "user")
//    private List<UserGaleryEntity> userGaleryEntity;

//    @JsonIgnore
//    @OneToOne(mappedBy = "user")
//    private UserMetricsEntity userMetricsEntity;

//    @JsonIgnore
//    @OneToMany(mappedBy = "user")
//    private List<TrainingEntity> trainingEntity;

    public UserEntity(Long id, String username, String email, String password, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserEntity() {

    }

    @Override
    public String getAuthority() {
        return this.getRole().getAuthority();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
