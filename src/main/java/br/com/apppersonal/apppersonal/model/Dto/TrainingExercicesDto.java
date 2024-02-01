package br.com.apppersonal.apppersonal.model.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingExercicesDto {
    private Long id;
    private String name;
    private Long trainingId;
    private LocalDate dayOfWeek;
    private String description;
    private int repetition;
    private double weight;
    private int repose;

    public TrainingExercicesDto(Long id, String name, Long trainingId, LocalDate dayOfWeek, String description, int repetition, double weight, int repose) {
        this.id = id;
        this.name = name;
        this.trainingId = trainingId;
        this.dayOfWeek = dayOfWeek;
        this.description = description;
        this.repetition = repetition;
        this.weight = weight;
        this.repose = repose;
    }
}
