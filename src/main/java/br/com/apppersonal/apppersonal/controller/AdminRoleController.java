package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.security.Role;
import br.com.apppersonal.apppersonal.service.AdminRoleService;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-role")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRoleController {

    private final AdminRoleService adminRoleService;

    private final ApiResponse apiResponse;

    @Autowired
    public AdminRoleController(AdminRoleService adminRoleService, ApiResponse apiResponse) {
        this.adminRoleService = adminRoleService;
        this.apiResponse = apiResponse;
    }

    @PostMapping("/change-role/admin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse> changeRoleAdmin(@PathVariable Long id) {
       return apiResponse.request(adminRoleService.ChangeUserRole(id, Role.ADMIN));
    }

    @PostMapping("/change-role/personal/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse> changeRolePersonal(@PathVariable Long id) {
       return apiResponse.request(adminRoleService.ChangeUserRole(id, Role.PERSONAL));
    }

    @PostMapping("/change-role/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse> changeRoleUser(@PathVariable Long id) {
       return apiResponse.request(adminRoleService.ChangeUserRole(id, Role.USER));
    }
}
