package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.model.entitys.ExerciseEntity;
import br.com.apppersonal.apppersonal.model.entitys.TrainingEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.repositorys.ExerciseRepository;
import br.com.apppersonal.apppersonal.model.repositorys.TrainingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingDayWeekService {
    private final TrainingRepository trainingRepository;

    private final UserService userService;

    private final ExerciseRepository exerciseRepository;

    public TrainingDayWeekService(TrainingRepository trainingRepository, UserService userService,
                                  ExerciseRepository exerciseRepository) {
        this.trainingRepository = trainingRepository;
        this.userService = userService;
        this.exerciseRepository = exerciseRepository;
    }

    public void createTrainingDayWeek(Long userId, String dayOfWeek, List<ExerciseEntity> exerciseEntity) {
        if (exerciseEntity.isEmpty()) throw new RuntimeException("Exercício não pode ser vazio");
        if (dayOfWeek.isEmpty()) throw new RuntimeException("Dia da semana não pode ser vazio");
        if (userId == null) throw new RuntimeException("Usuário não pode ser vazio");

        UserEntity user = userService.getUserById(userId);

        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setUser(user);
        trainingEntity.setDayOfWeek(dayOfWeek);

        for (ExerciseEntity exercise : exerciseEntity) {
            exercise.setTraining(trainingEntity);
        }

        trainingEntity.setExercise(exerciseEntity);
        trainingRepository.save(trainingEntity);
    }
}
