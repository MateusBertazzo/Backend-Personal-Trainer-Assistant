package br.com.apppersonal.apppersonal.model.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "training")
public class TrainingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "training")
    private List<ExerciseEntity> exercise;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    public TrainingEntity() {}
    public TrainingEntity(Long id, UserEntity user, String dayOfWeek) {
        this.id = id;
        this.user = user;
        this.dayOfWeek = dayOfWeek;
    }

    public TrainingEntity(UserEntity user) {
        this.user = user;
    }
}