package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.entitys.ExerciseEntity;
import org.springframework.http.HttpStatus;
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
    public void createTrainingDayWeek(@PathVariable Long userId,
                                      @RequestBody String dayOfWeek,
                                      @RequestBody List<ExerciseEntity> exerciseEntity) {
        trainingDayWeekService.createTrainingDayWeek(userId, dayOfWeek, exerciseEntity);
    }
}
