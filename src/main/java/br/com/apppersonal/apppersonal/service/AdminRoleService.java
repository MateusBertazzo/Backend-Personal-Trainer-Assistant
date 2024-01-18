package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.ParameterNullException;
import br.com.apppersonal.apppersonal.exceptions.UserNotFoundException;
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

    public void ChangeUserRole(Long id, Role newRole) {
        if (id == null) throw new ParameterNullException();
        if (newRole == null) throw new ParameterNullException();

        UserEntity user = userService.getUserById(id);

        if (user == null) throw new UserNotFoundException();

        user.setRole(newRole);

        userRepository.save(user);
    }
}
