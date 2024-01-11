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

    public void createTrainingDayWeek(Long userId, String dayOfWeek, List<ExerciseEntity> exerciseEntityList) {
        if (exerciseEntityList.isEmpty()) throw new RuntimeException("Exercício não pode ser vazio");
        if (dayOfWeek.isEmpty()) throw new RuntimeException("Dia da semana não pode ser vazio");
        if (userId == null) throw new RuntimeException("Usuário não pode ser vazio");

        UserEntity user = userService.getUserById(userId);

        if (user == null) throw new RuntimeException("Usuário não encontrado");

        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setUser(user);
        trainingEntity.setDayOfWeek(dayOfWeek);

        exerciseEntityList.forEach(exercise -> exercise.setTraining(trainingEntity));

        trainingRepository.save(trainingEntity);

        exerciseRepository.saveAll(exerciseEntityList);
    }

    public List<ExerciseEntity> getExerciseByTrainingId(Long trainingId) {
        if (trainingId == null) throw new RuntimeException("Usuário não pode ser vazio");

        List exerciseEntity = exerciseRepository.findAllByTrainingId(trainingId);

        if (exerciseEntity.isEmpty()) throw new RuntimeException("Treino não encontrado");

        return  exerciseEntity;
    }
}
