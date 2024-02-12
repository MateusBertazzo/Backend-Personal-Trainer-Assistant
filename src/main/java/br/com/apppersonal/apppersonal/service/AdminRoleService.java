package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.ParameterNullException;
import br.com.apppersonal.apppersonal.exceptions.UserNotFoundException;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.repositorys.UserRepository;
import br.com.apppersonal.apppersonal.security.Role;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import lombok.Data;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;

@Service
public class AdminRoleService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public AdminRoleService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Método para alterar a role do usuário
     *
     * @param   Long id
     * @param   Role newRole
     * @return  ResponseEntity
     */
    public ResponseEntity<?> ChangeUserRole(Long id, Role newRole) {
        try {
            if (id == null) throw new ParameterNullException("Identificador do usuário não informado");

            if (newRole == null) throw new ParameterNullException("Nova role não informada");

            UserEntity user = userService.getUserById(id);

            if (user == null) throw new UserNotFoundException("Usuário não encontrado");

            user.setRole(newRole);
            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Role alterada com sucesso!"
                            )
                    );

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    false,
                                    e.getMessage()
                            )
                    );
        }
    }
}
