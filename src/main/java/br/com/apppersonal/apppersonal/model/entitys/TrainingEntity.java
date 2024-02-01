package br.com.apppersonal.apppersonal.model.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "training")
public class TrainingEntity extends BaseEntity{

    @JsonIgnore
    @OneToMany(mappedBy = "training")
    private List<ExerciseEntity> exercise;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


    @Column(name = "day_of_week")
    private LocalDate dayOfWeek;

    public TrainingEntity() {}
    public TrainingEntity(UserEntity user, LocalDate dayOfWeek) {
        this.user = user;
        this.dayOfWeek = dayOfWeek;
    }

    public TrainingEntity(UserEntity user) {
        this.user = user;
    }
}
