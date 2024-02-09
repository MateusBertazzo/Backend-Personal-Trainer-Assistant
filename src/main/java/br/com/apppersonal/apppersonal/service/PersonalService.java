package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.ParameterNullException;
import br.com.apppersonal.apppersonal.exceptions.UserNotFoundException;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.repositorys.UserRepository;
import br.com.apppersonal.apppersonal.security.Role;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PersonalService {
    private UserRepository userRepository;

    public PersonalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> associateUserWithPersonal(Long userId, Long personalId) {
        try {
            if (userId == null) throw new ParameterNullException("Identificador do usuário não informado");
            if (personalId == null) throw new ParameterNullException("Identificador do personal trainer não informado");

            UserEntity aluno = userRepository.findByIdAndRole(userId, Role.USER);

            if (aluno == null) {
                throw new UserNotFoundException("Usuário não encontrado");
            }

            aluno.setPersonalTrainerId(personalId); // Associa o usuário ao personal trainer
            userRepository.save(aluno);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Usuário associado com sucesso ao personal trainer"
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
    public ResponseEntity<?> dissociateUserFromPersonal(Long userId) {
        try {
            if (userId == null) throw new ParameterNullException("Identificador do usuário não informado");

            UserEntity aluno = userRepository.findByIdAndRole(userId, Role.USER);

            if (aluno == null) {
                throw new UserNotFoundException("Usuário não encontrado");
            }

            aluno.setPersonalTrainerId(null); // Remove a associação com o personal trainer
            userRepository.save(aluno);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Usuário dissociado com sucesso do personal trainer"
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
