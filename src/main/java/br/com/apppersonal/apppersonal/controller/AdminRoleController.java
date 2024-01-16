package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.security.Role;
import br.com.apppersonal.apppersonal.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-role")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRoleController {

    private final AdminRoleService adminRoleService;

    @Autowired
    public AdminRoleController(AdminRoleService adminRoleService) {
        this.adminRoleService = adminRoleService;
    }

    @PostMapping("/change-role/admin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void changeRoleAdmin(@PathVariable Long id) {
        adminRoleService.ChangeUserRole(id, Role.ADMIN);
    }

    @PostMapping("/change-role/personal/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void changeRolePersonal(@PathVariable Long id) {
        adminRoleService.ChangeUserRole(id, Role.PERSONAL);
    }

    @PostMapping("/change-role/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void changeRoleUser(@PathVariable Long id) {
        adminRoleService.ChangeUserRole(id, Role.USER);
    }
}
