package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.NotFoundUserMetrics;
import br.com.apppersonal.apppersonal.exceptions.ParameterNullException;
import br.com.apppersonal.apppersonal.exceptions.UpdateUserMetricsException;
import br.com.apppersonal.apppersonal.model.Dto.UserMetricsDto;
import br.com.apppersonal.apppersonal.model.entitys.UserMetricsEntity;
import br.com.apppersonal.apppersonal.model.repositorys.UserMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class UserMetricsService {

    private final UserMetricsRepository userMetricsRepository;

    @Autowired
    public UserMetricsService(UserMetricsRepository userMetricsRepository) {
        this.userMetricsRepository = userMetricsRepository;
    }

    public void updateUserMetrics(UserMetricsEntity userMetricsEntity) {
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
        } catch (Exception e) {
            throw new UpdateUserMetricsException();
        }

    }

    public UserMetricsDto getUserMetricsByUserId(Long userId) {

        if (userId == null) {
            throw new ParameterNullException();
        }

        UserMetricsEntity userMetrics = userMetricsRepository.findByUserId(userId);

        if (userMetrics == null) {
            throw new NotFoundUserMetrics();
        }

        return new UserMetricsDto(
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
    }


}
