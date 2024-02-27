package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.Dto.ProfileDto;
import br.com.apppersonal.apppersonal.model.Dto.UserProfileDto;
import br.com.apppersonal.apppersonal.service.ProfileService;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileService profileService;

    private final ApiResponse apiResponse;

    public ProfileController(ProfileService profileService, ApiResponse apiResponse) {
        this.profileService = profileService;
        this.apiResponse = apiResponse;
    }

    /**
     * Método para atualizar um perfil
     *
     * @param   ProfileDto profileDto
     * @param   Long userId
     * @return  ResponseEntity
     */
    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('USER') or hasRole('PERSONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateProfile(@PathVariable Long id, @RequestBody ProfileDto profileDto) {
        return apiResponse.request(profileService.updateProfile(id, profileDto));
    }

    /**
     * Método para listar todos os perfis
     *
     * @return  ResponseEntity
     */
    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PERSONAL')")
    public ResponseEntity<ApiResponse> getAllProfiles() {
       return apiResponse.request(profileService.getAllProfiles());
    }

    /**
     * Método para listar um perfil por id
     *
     * @param   Long personalId
     * @return  ResponseEntity
     */
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('PERSONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getProfileById(@PathVariable Long id) {
        return apiResponse.request(profileService.getProfileById(id));
    }
}
