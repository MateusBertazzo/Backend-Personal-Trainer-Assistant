package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.entitys.UserMetricsEntity;
import br.com.apppersonal.apppersonal.service.UserMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('PERSONAL')")
    private void updateUserMetrics(@PathVariable Long userId, @RequestBody UserMetricsEntity userMetricsEntity) {
        int i = 0;
        userMetricsService.updateUserMetrics(userId, userMetricsEntity);
    }

    @GetMapping("/{userId}/get")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('PERSONAL')")
    private UserMetricsEntity getUserMetricsByUserId(@PathVariable Long userId) {
        return userMetricsService.getUserMetricsByUserId(userId);
    }
}
