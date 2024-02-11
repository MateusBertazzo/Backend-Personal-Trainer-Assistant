package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.ExercisesNotFound;
import br.com.apppersonal.apppersonal.exceptions.ParameterNullException;
import br.com.apppersonal.apppersonal.exceptions.UserNotFoundException;
import br.com.apppersonal.apppersonal.model.Dto.CreateTrainingDto;
import br.com.apppersonal.apppersonal.model.Dto.TrainingExercicesDto;
import br.com.apppersonal.apppersonal.model.entitys.ExerciseEntity;
import br.com.apppersonal.apppersonal.model.entitys.TrainingEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.repositorys.ExerciseRepository;
import br.com.apppersonal.apppersonal.model.repositorys.TrainingRepository;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    /**
     * Método para criar um treino
     *
     * @param   CreateTrainingDto createTrainingDto
     * @return  ResponseEntity
     */
    public ResponseEntity<?> createTrainingDayWeek(CreateTrainingDto createTrainingDto) {
        try {

            if (createTrainingDto.getDayOfWeek() == null) throw new ParameterNullException("Dia da semana não pode ser vazio");

            if (createTrainingDto.getExerciseEntityList() == null) throw new ParameterNullException("Exercícios não podem ser vazios");

            if (createTrainingDto.getUserId() == null) throw new ParameterNullException("Usuário não pode ser vazio");

            UserEntity user = userService.getUserById(createTrainingDto.getUserId());

            if (user == null) throw new UserNotFoundException("Usuário não encontrado");

            TrainingEntity trainingEntity = new TrainingEntity();

            trainingEntity.setUser(user);
            trainingEntity.setDayOfWeek(createTrainingDto.getDayOfWeek());

            createTrainingDto.getExerciseEntityList().forEach(exercise -> exercise.setTraining(trainingEntity));

            trainingRepository.save(trainingEntity);

            exerciseRepository.saveAll(createTrainingDto.getExerciseEntityList());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Treino criado com sucesso"
                            )
                    );

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    false,
                                    e.getMessage()
                            )
                    );
        }

    }

    /**
     * Método para retornar os exercícios do treino pelo id
     *
     * @param   Long trainingId
     * @return  ResponseEntity
     */
    public ResponseEntity<?> getExerciseByTrainingId(Long trainingId) {

        try {
            if (trainingId == null) throw new ParameterNullException("Identificador do treino não informado");

            // verificar se o treino não está deletado
            Boolean isTrue = trainingRepository.isDeletedTraining(trainingId);

            if (!isTrue) throw new ExercisesNotFound("Treino não encontrado");

            List<ExerciseEntity> exerciseEntities = exerciseRepository.findAllByTrainingId(trainingId);

            if (exerciseEntities.isEmpty()) throw new ExercisesNotFound("Exercícios não encontrados");


            List<TrainingExercicesDto> responseDtoList = new ArrayList<>();

            for (ExerciseEntity exerciseEntity : exerciseEntities) {
                responseDtoList
                        .add(
                                new TrainingExercicesDto(
                                        exerciseEntity.getId(),
                                        exerciseEntity.getName(),
                                        exerciseEntity.getTraining().getId(),
                                        exerciseEntity.getTraining().getDayOfWeek(),
                                        exerciseEntity.getDescription(),
                                        exerciseEntity.getRepetition(),
                                        exerciseEntity.getWeight(),
                                        exerciseEntity.getRepose()
                                )
                        );
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Exercícios encontrados com sucesso",
                                    responseDtoList
                            )
                    );
        } catch (ExercisesNotFound e)  {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    false,
                                    e.getMessage()
                            )
                    );
        } catch (Exception e)  {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    false,
                                    e.getMessage()
                            )
                    );
        }
    }
}
