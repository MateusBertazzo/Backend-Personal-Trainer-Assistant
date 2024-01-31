package br.com.apppersonal.apppersonal.model.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "exercises")
public class ExerciseEntity extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private TrainingEntity training;

    private String description;

    private int repetition;

    private double weight;

    private int repose;

    public ExerciseEntity() {}

    public ExerciseEntity(
            String name,
            TrainingEntity training,
            String description,
            int repetition,
            double weight,
            int repose
    )
    {
        this.name = name;
        this.training = training;
        this.description = description;
        this.repetition = repetition;
        this.weight = weight;
        this.repose = repose;
    }

    public ExerciseEntity(TrainingEntity training) {
        this.training = training;
    }
}
