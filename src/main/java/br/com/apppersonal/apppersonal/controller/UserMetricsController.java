package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.entitys.UserMetricsEntity;
import br.com.apppersonal.apppersonal.service.UserMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-metrics")
public class UserMetricsController {

    private final UserMetricsService userMetricsService;

    @Autowired
    public UserMetricsController(UserMetricsService userMetricsService) {
        this.userMetricsService = userMetricsService;
    }

    @PutMapping("/{userId}/update")
    @ResponseStatus(HttpStatus.OK)
    private void addUserMetrics(@PathVariable Long userId, @RequestBody UserMetricsEntity userMetricsEntity) {
        userMetricsService.updateUserMetrics(userId, userMetricsEntity);
    }
}
