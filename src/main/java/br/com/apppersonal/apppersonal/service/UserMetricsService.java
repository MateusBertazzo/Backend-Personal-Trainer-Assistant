package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.model.entitys.UserMetricsEntity;
import br.com.apppersonal.apppersonal.model.repositorys.UserMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMetricsService {

    private final UserMetricsRepository userMetricsRepository;

    @Autowired
    public UserMetricsService(UserMetricsRepository userMetricsRepository) {
        this.userMetricsRepository = userMetricsRepository;
    }

    public void addUserMetrics(UserMetricsEntity userMetricsEntity) {
        userMetricsRepository.save(userMetricsEntity);
    }
}
