package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.Dto.TrainingExercicesDto;
import br.com.apppersonal.apppersonal.model.entitys.ExerciseEntity;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import br.com.apppersonal.apppersonal.service.TrainingDayWeekService;

import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingDayWeekController {
    private final TrainingDayWeekService trainingDayWeekService;

    private final ApiResponse apiResponse;

    public TrainingDayWeekController(TrainingDayWeekService trainingDayWeekService, ApiResponse apiResponse) {
        this.trainingDayWeekService = trainingDayWeekService;
        this.apiResponse = apiResponse;
    }

    @PostMapping("/create/{userId}")
    @PreAuthorize("hasRole('PERSONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createTrainingDayWeek(@PathVariable Long userId,
                                                @RequestParam String dayOfWeek,
                                                @RequestBody List<ExerciseEntity> exerciseEntity) {
       return apiResponse.request
               (trainingDayWeekService.createTrainingDayWeek(userId, dayOfWeek, exerciseEntity));
    }

    @GetMapping("/get/{trainingId}")
    @PreAuthorize("hasRole('PERSONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getExerciseByTrainingId(@PathVariable Long trainingId) {
        return apiResponse.request(trainingDayWeekService.getExerciseByTrainingId(trainingId));
    }
}
