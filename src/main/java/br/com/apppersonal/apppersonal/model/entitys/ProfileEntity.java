package br.com.apppersonal.apppersonal.model.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "profiles")
public class ProfileEntity extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String photo;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    private String observation;

    private String goal;

    public ProfileEntity(UserEntity user, String photo, String phoneNumber, String observation, String goal) {
        this.user = user;
        this.photo = photo;
        this.phoneNumber = phoneNumber;
        this.observation = observation;
        this.goal = goal;
    }

    public ProfileEntity() {}

    public ProfileEntity(UserEntity userEntity) {
        this.user = userEntity;
    }
}
