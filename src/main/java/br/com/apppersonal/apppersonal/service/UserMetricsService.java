package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.NotFoundUserMetrics;
import br.com.apppersonal.apppersonal.exceptions.ParameterNullException;
import br.com.apppersonal.apppersonal.model.Dto.UserMetricsDto;
import br.com.apppersonal.apppersonal.model.entitys.UserMetricsEntity;
import br.com.apppersonal.apppersonal.model.repositorys.UserMetricsRepository;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class UserMetricsService {

    private final UserMetricsRepository userMetricsRepository;

    @Autowired
    public UserMetricsService(UserMetricsRepository userMetricsRepository) {
        this.userMetricsRepository = userMetricsRepository;
    }

    /**
     * Método para atualizar as medidas do usuário
     *
     * @param   UserMetricsEntity userMetricsEntity
     * @return  ResponseEntity
     */
    public ResponseEntity<?> updateUserMetrics(UserMetricsEntity userMetricsEntity, Long userId) {
        try {

            if (userMetricsEntity == null ) throw new ParameterNullException("Parâmetros não informados");

            // busco o usuario pelo id
            UserMetricsEntity userMetrics = userMetricsRepository.findById(userId)
                    .orElseThrow(NotFoundUserMetrics::new);

            // verifico se o usuario foi deletado
            if (userMetrics.getUser().getDeleted()) throw new NotFoundUserMetrics("Usuário deletado");

            userMetrics.setDataStart(LocalDate.now());
            userMetrics.setWeight(userMetricsEntity.getWeight());
            userMetrics.setHeight(userMetricsEntity.getHeight());
            userMetrics.setAge(userMetricsEntity.getAge());
            userMetrics.setTorso(userMetricsEntity.getTorso());
            userMetrics.setHip(userMetricsEntity.getHip());
            userMetrics.setLeftArm(userMetricsEntity.getLeftArm());
            userMetrics.setRightArm(userMetricsEntity.getRightArm());
            userMetrics.setLeftLeg(userMetricsEntity.getLeftLeg());
            userMetrics.setRightLeg(userMetricsEntity.getRightLeg());
            userMetrics.setLeftCalf(userMetricsEntity.getLeftCalf());
            userMetrics.setRightCalf(userMetricsEntity.getRightCalf());

            userMetricsRepository.save(userMetrics);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Medidas atualizados com sucesso!"
                            )
                    );
        } catch (NotFoundUserMetrics e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    false,
                                    e.getMessage()
                            )
                    );
        }  catch (Exception e) {
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
     * Método para retornar as medidas do usuário pelo id
     *
     * @param   Long userId
     * @return  ResponseEntity
     */
    public ResponseEntity<?> getUserMetricsByUserId(Long userId) {
        try {
            if (userId == null) throw new ParameterNullException("Usuário não informado");

            UserMetricsEntity userMetrics = userMetricsRepository.findByUserId(userId);

            if (userMetrics == null) throw new NotFoundUserMetrics("Medidas não encontradas para este usuario");

            var metrics = new UserMetricsDto(
                    userMetrics.getId(),
                    userMetrics.getUser().getId(),
                    userMetrics.getUser().getRole(),
                    userMetrics.getDataStart(),
                    userMetrics.getWeight(),
                    userMetrics.getHeight(),
                    userMetrics.getAge(),
                    userMetrics.getTorso(),
                    userMetrics.getHip(),
                    userMetrics.getLeftArm(),
                    userMetrics.getRightArm(),
                    userMetrics.getLeftLeg(),
                    userMetrics.getRightLeg(),
                    userMetrics.getLeftCalf(),
                    userMetrics.getRightCalf()
            );

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Medidas retornadas com sucesso!",
                                    metrics
                            )
                    );
        }   catch (Exception e) {
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
