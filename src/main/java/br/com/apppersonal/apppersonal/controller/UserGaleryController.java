package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.Dto.GaleryDto;
import br.com.apppersonal.apppersonal.service.UserGaleryService;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/user-galery")
public class UserGaleryController {
    private final UserGaleryService userGaleryService;

    private final ApiResponse apiResponse;

    @Autowired
    public UserGaleryController(UserGaleryService userGaleryService, ApiResponse apiResponse) {
        this.userGaleryService = userGaleryService;
        this.apiResponse = apiResponse;
    }

    @PostMapping("/{userId}/save")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('PERSONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> saveFotoGalery(@PathVariable Long userId, @RequestBody String urlFoto) {
        return apiResponse.request(userGaleryService.postFoto(userId, urlFoto));
    }

    @GetMapping("/get-fotos/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('PERSONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getFotos(@PathVariable Long userId) {
      return apiResponse.request(userGaleryService.getFotosById(userId));
    }
}
