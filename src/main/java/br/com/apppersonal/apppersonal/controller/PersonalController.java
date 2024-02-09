package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.service.PersonalService;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personal")
public class PersonalController {

    private final PersonalService personalService;

    private final ApiResponse apiResponse;

    @Autowired
    public PersonalController(PersonalService personalService, ApiResponse apiResponse) {
        this.personalService = personalService;
        this.apiResponse = apiResponse;
    }

    @PostMapping("/{personalId}/associate-user/{userId}")
    @PreAuthorize("hasRole('PERSONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> associateUserWithPersonal(@PathVariable Long userId, @PathVariable Long personalId) {
        return apiResponse.request(personalService.associateUserWithPersonal(userId, personalId));
    }
}
