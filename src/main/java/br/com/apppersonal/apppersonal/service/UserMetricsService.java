package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.model.entitys.UserMetricsEntity;
import br.com.apppersonal.apppersonal.model.repositorys.UserMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMetricsService {

    private final UserMetricsRepository userMetricsRepository;
    private final UserService userService;

    @Autowired
    public UserMetricsService(UserMetricsRepository userMetricsRepository, UserService userService) {
        this.userMetricsRepository = userMetricsRepository;
        this.userService = userService;
    }

    public void updateUserMetrics(Long userId, UserMetricsEntity userMetricsEntity) {
        UserMetricsEntity userMetrics = userMetricsRepository.findByUserId(userId);

        if (userMetrics == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        userMetrics.setUser(userService.getUserById(userId));
        userMetrics.setDataStart(userMetricsEntity.getDataStart());
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

        userMetricsRepository.save(userMetricsEntity);
    }
}
