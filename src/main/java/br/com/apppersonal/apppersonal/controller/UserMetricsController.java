package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.Dto.UserMetricsDto;
import br.com.apppersonal.apppersonal.model.entitys.UserMetricsEntity;
import br.com.apppersonal.apppersonal.service.UserMetricsService;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-metrics")
public class UserMetricsController {
    private final UserMetricsService userMetricsService;

    private final ApiResponse apiResponse;

    @Autowired
    public UserMetricsController(UserMetricsService userMetricsService, ApiResponse apiResponse) {
        this.userMetricsService = userMetricsService;
        this.apiResponse = apiResponse;
    }

    /**
     * Método para atualizar medidas do usuário
     *
     * @param   UserMetricsDto userMetricsDto
     * @return  ResponseEntity
     */
    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse> updateUserMetrics(@RequestBody UserMetricsEntity userMetricsEntity,@PathVariable Long userId) {
        return apiResponse.request(userMetricsService.updateUserMetrics(userMetricsEntity, userId));
    }

    /**
     * Método para listar todas as medidas do usuário por id
     *
     * @param   Long userId
     * @return  ResponseEntity
     */
    @GetMapping("/{userId}/get")
    @PreAuthorize("hasRole('PERSONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getUserMetricsByUserId(@PathVariable Long userId) {
        return apiResponse.request(userMetricsService.getUserMetricsByUserId(userId));
    }
}
