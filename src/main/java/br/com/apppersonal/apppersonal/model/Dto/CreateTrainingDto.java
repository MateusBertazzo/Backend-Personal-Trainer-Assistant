package br.com.apppersonal.apppersonal.model.Dto;

import br.com.apppersonal.apppersonal.model.entitys.ExerciseEntity;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreateTrainingDto {
    private Long userId;
    private LocalDate dayOfWeek;
    private List<ExerciseEntity> exerciseEntityList;
    public CreateTrainingDto(Long userId, LocalDate dayOfWeek, List<ExerciseEntity> exerciseEntityList) {
        this.userId = userId;
        this.dayOfWeek = dayOfWeek;
        this.exerciseEntityList = exerciseEntityList;

    }
}
