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

    /**
     * Método para associar um usuário a um personal
     *
     * @param   Long userId
     * @param   Long personalId
     * @return  ResponseEntity
     */
    @PostMapping("/{personalId}/associate-user/{userId}")
    @PreAuthorize("hasRole('PERSONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> associateUserWithPersonal(@PathVariable Long userId, @PathVariable Long personalId) {
        return apiResponse.request(personalService.associateUserWithPersonal(userId, personalId));
    }

    /**
     * Método para desassociar um usuário de um personal
     *
     * @param   Long userId
     * @return  ResponseEntity
     */
    @PostMapping("/{userId}/dissociate-user")
    @PreAuthorize("hasRole('PERSONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> dissociateUserFromPersonal(@PathVariable Long userId) {
        return apiResponse.request(personalService.dissociateUserFromPersonal(userId));
    }

    /**
     * Método para listar todos os alunos associados a um personal
     *
     * @param   Long personalId
     * @return  ResponseEntity
     */
    @GetMapping("/get-all/students-by-personal/{personalId}")
    @PreAuthorize("hasRole('PERSONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllStudentsByPersonal(@PathVariable Long personalId) {
        return apiResponse.request(personalService.listStudentsByPersonal(personalId));
    }
}
