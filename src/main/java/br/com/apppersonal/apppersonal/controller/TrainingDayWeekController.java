package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.Dto.TrainingExercicesDto;
import br.com.apppersonal.apppersonal.model.entitys.ExerciseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import br.com.apppersonal.apppersonal.service.TrainingDayWeekService;

import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingDayWeekController {
    private final TrainingDayWeekService trainingDayWeekService;

    public TrainingDayWeekController(TrainingDayWeekService trainingDayWeekService) {
        this.trainingDayWeekService = trainingDayWeekService;
    }

    @PostMapping("/create/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PERSONAL')")
    public void createTrainingDayWeek(@PathVariable Long userId,
                                      @RequestParam String dayOfWeek,
                                      @RequestBody List<ExerciseEntity> exerciseEntity) {
        trainingDayWeekService.createTrainingDayWeek(userId, dayOfWeek, exerciseEntity);
    }

    @GetMapping("/get/{trainingId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TrainingExercicesDto> getExerciseByTrainingId(@PathVariable Long trainingId) {
        return trainingDayWeekService.getExerciseByTrainingId(trainingId);
    }
}
