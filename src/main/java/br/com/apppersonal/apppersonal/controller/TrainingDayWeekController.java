package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.entitys.ExerciseEntity;
import br.com.apppersonal.apppersonal.model.entitys.TrainingEntity;
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
                                      @RequestParam String dayOfWeek,
                                      @RequestBody List<ExerciseEntity> exerciseEntity) {
        trainingDayWeekService.createTrainingDayWeek(userId, dayOfWeek, exerciseEntity);
    }

    @GetMapping("/get/{trainingId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ExerciseEntity> getExerciseByTrainingId(@PathVariable Long trainingId) {
        return trainingDayWeekService.getExerciseByTrainingId(trainingId);
    }
//    TESTE
}
