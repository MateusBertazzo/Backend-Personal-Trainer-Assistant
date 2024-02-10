package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.ParameterNullException;
import br.com.apppersonal.apppersonal.exceptions.UserNotFoundException;
import br.com.apppersonal.apppersonal.model.Dto.UserPersonalDto;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.repositorys.UserRepository;
import br.com.apppersonal.apppersonal.security.Role;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalService {
    private UserRepository userRepository;

    @Autowired
    public PersonalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Método para asssociação de um personal trainer a um aluno
     *
     * @param  Long userId
     * @param  Long personalId
     * @return  ResponseEntity
     */
    public ResponseEntity<?> associateUserWithPersonal(Long userId, Long personalId) {
        try {
            if (userId == null) throw new ParameterNullException("Identificador do usuário não informado");

            if (personalId == null) throw new ParameterNullException("Identificador do personal trainer não informado");

            UserEntity students = userRepository.findByIdAndRole(userId, Role.USER);

            if (students == null) throw new UserNotFoundException("Usuário não encontrado");

            // Associa o usuário ao personal trainer
            students.setPersonalTrainerId(personalId);
            userRepository.save(students);

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

    /**
     * Método para desassociação de um personal trainer a um aluno
     *
     * @param  Long userId
     * @return  ResponseEntity
     */
    public ResponseEntity<?> dissociateUserFromPersonal(Long userId) {
        try {
            if (userId == null) throw new ParameterNullException("Identificador do usuário não informado");

            UserEntity students = userRepository.findByIdAndRole(userId, Role.USER);

            if (students == null) throw new UserNotFoundException("Usuário não encontrado");

            // Remove a associação com o personal trainer
            students.setPersonalTrainerId(null);
            userRepository.save(students);

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

    /**
     * Método para listar alunos associados a um personal trainer
     *
     * @param  Long personalId
     * @return  ResponseEntity
     */

    public ResponseEntity<?> listStudentsByPersonal(Long personalId) {
        try {
            if (personalId == null) throw new ParameterNullException("Identificador do personal trainer não informado");

            // busca por todos users com role USER e que tenham o personal trainer id associado
            List<UserEntity> students = userRepository.findByRoleAndPersonalTrainerId(Role.USER, personalId);

            if (students.isEmpty()) throw new UserNotFoundException("Nenhum aluno encontrado");

            List<UserPersonalDto> listStudents =
                    students
                            .stream()
                            .map(
                                    student -> new UserPersonalDto(
                                            student.getId(),
                                            student.getUsername(),
                                            student.getEmail(),
                                            student.getRole(),
                                            student.getProfile().getNumeroTelefone()
                                    )
                            )
                            .collect(Collectors.toList());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Lista de alunos retornada com sucesso",
                                    listStudents
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
