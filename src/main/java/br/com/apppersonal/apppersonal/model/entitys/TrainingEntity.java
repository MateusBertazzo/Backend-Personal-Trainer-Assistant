package br.com.apppersonal.apppersonal.model.entitys;

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

    @OneToMany
    @JoinColumn(name = "training_id")
    private List<ExerciseEntity> exercise;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String dayOfWeek;

    public TrainingEntity() {}
    public TrainingEntity(Long id, UserEntity user, String dayOfWeek) {
        this.id = id;
        this.user = user;
        this.dayOfWeek = dayOfWeek;
    }
}
