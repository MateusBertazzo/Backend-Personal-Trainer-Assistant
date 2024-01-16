package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.repositorys.UserRepository;
import br.com.apppersonal.apppersonal.security.Role;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public AdminRoleService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

//    public void ChangeRoleAdmin(Long id) {
//        if (id == null) throw new RuntimeException("Id não pode ser vazio");
//
//        UserEntity user = userService.getUserById(id);
//
//        if (user == null) throw new RuntimeException("Usuário não encontrado");
//
//        user.setRole(Role.ADMIN);
//
//        userRepository.save(user);
//    }
//
//    public void ChangeRolePersonal(Long id) {
//        if (id == null) throw new RuntimeException("Id não pode ser vazio");
//
//        UserEntity user = userService.getUserById(id);
//
//        if (user == null) throw new RuntimeException("Usuário não encontrado");
//
//        user.setRole(Role.PERSONAL);
//
//        userRepository.save(user);
//    }
//
//    public void ChangeRoleUser(Long id) {
//        if (id == null) throw new RuntimeException("Id não pode ser vazio");
//
//        UserEntity user = userService.getUserById(id);
//
//        if (user == null) throw new RuntimeException("Usuário não encontrado");
//
//        user.setRole(Role.USER);
//
//        userRepository.save(user);
//    }

    public void ChangeUserRole(Long id, Role newRole) {
        if (id == null) throw new RuntimeException("Id não pode ser vazio");
        if (newRole == null) throw new RuntimeException("Role não pode ser vazio");

        UserEntity user = userService.getUserById(id);

        if (user == null) throw new RuntimeException("Usuário não encontrado");

        user.setRole(newRole);

        userRepository.save(user);
    }
}
