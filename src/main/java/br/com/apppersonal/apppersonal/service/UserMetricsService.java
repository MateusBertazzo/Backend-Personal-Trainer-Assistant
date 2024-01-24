package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.NotFoundUserMetrics;
import br.com.apppersonal.apppersonal.exceptions.ParameterNullException;
import br.com.apppersonal.apppersonal.exceptions.UpdateUserMetricsException;
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

    public ResponseEntity<?> updateUserMetrics(UserMetricsEntity userMetricsEntity) {
        if (userMetricsEntity == null ) {
            throw new ParameterNullException();
        }

        UserMetricsEntity userMetrics = userMetricsRepository.findById(userMetricsEntity.getUser().getId())
                .orElseThrow(NotFoundUserMetrics::new);

        try {
            userMetrics.setDataStart(LocalDate.now());
            userMetrics.setWeight(userMetricsEntity.getWeight());
            userMetrics.setHeight(userMetricsEntity.getHeight());
            userMetrics.setAge(userMetricsEntity.getAge());
            userMetrics.setTronco(userMetricsEntity.getTronco());
            userMetrics.setQuadril(userMetricsEntity.getQuadril());
            userMetrics.setBracoEsquerdo(userMetricsEntity.getBracoEsquerdo());
            userMetrics.setBracoDireito(userMetricsEntity.getBracoDireito());
            userMetrics.setPernaEsquerda(userMetricsEntity.getPernaEsquerda());
            userMetrics.setPernaDireita(userMetricsEntity.getPernaDireita());
            userMetrics.setPanturrilhaEsquerda(userMetricsEntity.getPanturrilhaEsquerda());
            userMetrics.setPanturrilhaDireita(userMetricsEntity.getPanturrilhaDireita());

            userMetricsRepository.save(userMetrics);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Medidas atualizados com sucesso!"
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

    public ResponseEntity<?> getUserMetricsByUserId(Long userId) {
        try {
            if (userId == null) {
                throw new ParameterNullException();
            }

            UserMetricsEntity userMetrics = userMetricsRepository.findByUserId(userId);

            if (userMetrics == null) {
                throw new NotFoundUserMetrics();
            }

            var metrics = new UserMetricsDto(
                    userMetrics.getId(),
                    userMetrics.getUser().getId(),
                    userMetrics.getUser().getRole(),
                    userMetrics.getDataStart(),
                    userMetrics.getWeight(),
                    userMetrics.getHeight(),
                    userMetrics.getAge(),
                    userMetrics.getTronco(),
                    userMetrics.getQuadril(),
                    userMetrics.getBracoEsquerdo(),
                    userMetrics.getBracoDireito(),
                    userMetrics.getPernaEsquerda(),
                    userMetrics.getPernaDireita(),
                    userMetrics.getPanturrilhaEsquerda(),
                    userMetrics.getPanturrilhaDireita()
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
