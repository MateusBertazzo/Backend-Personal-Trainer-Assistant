package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.*;
import br.com.apppersonal.apppersonal.model.Dto.EmailRequestDto;
import br.com.apppersonal.apppersonal.model.Dto.ResetPasswordDto;
import br.com.apppersonal.apppersonal.model.Dto.ResetPasswordForgotDto;
import br.com.apppersonal.apppersonal.model.Dto.UserCreateDto;
import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserMetricsEntity;
import br.com.apppersonal.apppersonal.model.entitys.VerificationCodeEntity;
import br.com.apppersonal.apppersonal.model.repositorys.ProfileRepository;
import br.com.apppersonal.apppersonal.model.repositorys.UserMetricsRepository;
import br.com.apppersonal.apppersonal.model.repositorys.UserRepository;
import br.com.apppersonal.apppersonal.model.repositorys.VerificationCodeRepository;
import br.com.apppersonal.apppersonal.security.Role;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserMetricsRepository userMetricsRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository,
                       UserMetricsRepository userMetricsRepository,
                       VerificationCodeRepository verificationCodeRepository,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.userMetricsRepository = userMetricsRepository;
        this.verificationCodeRepository = verificationCodeRepository;
        this.emailService = emailService;
    }

    /**
     * Método para criar um usuário
     *
     * @param   UserCreateDto userParameterDto
     * @return  ResponseEntity
     */
    public ResponseEntity<?> createUser(UserCreateDto userParameterDto) {

        try {

            if (userParameterDto == null) throw new ParameterNullException("Parâmetros não informados");

            //Verifico se a senha passada é igual a confirmação de senha
            if (!userParameterDto.getPassword().equals(userParameterDto.getConfirmPassword())) {
               throw new PasswordNotMatchException("Senhas não conferem");
            }

            UserEntity userEntity = new UserEntity();
            String hashedPassword = new BCryptPasswordEncoder().encode(userParameterDto.getPassword());
            userEntity.setPassword(hashedPassword);
            userEntity.setEmail(userParameterDto.getEmail());
            userEntity.setUsername(userParameterDto.getUsername());
            userEntity.setRole(Role.USER);

            UserEntity saveUser = userRepository.save(userEntity);

            ProfileEntity profileEntity = new ProfileEntity();
            UserMetricsEntity userMetricsEntity = new UserMetricsEntity();
            VerificationCodeEntity verificationCodeEntity = new VerificationCodeEntity();

            verificationCodeEntity.setUser(saveUser);
            profileEntity.setUser(saveUser);
            userMetricsEntity.setUser(saveUser);

            profileRepository.save(profileEntity);
            userMetricsRepository.save(userMetricsEntity);
            verificationCodeRepository.save(verificationCodeEntity);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Usuário criado com sucesso"
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
     * Método para buscar o usuário pelo id
     *
     * @param   Long id
     * @return  UserEntity
     */
    public UserEntity getUserById(Long id) {
        try {
            if (id == null) throw new ParameterNullException("Identificador do usuário não informado");

            UserEntity user =  userRepository.findByIdAndNotDeleted(id);

            if (user == null) throw new UserNotFoundException("Usuário não encontrado");

            return user;

        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UnauthorizedUserException();
        }

        return user;
    }

/**
     * Método para deletar usuario
     *
     * @param   Long id
     * @return  ResponseEntity
     */
    public ResponseEntity<?> deleteUser(Long id) {
        try {
            if (id == null) throw new ParameterNullException("Identificador do usuário não informado");

            UserEntity user = userRepository.findByIdAndNotDeleted(id);


            if (user == null) throw new UserNotFoundException("Usuário não encontrado");

            user.getProfile().setDeleted(true);
            user.getUserMetrics().setDeleted(true);
            user.getVerificationCode().setDeleted(true);

            user.getTraining()
                    .forEach(training -> training.setDeleted(true));

            user.getTraining()
                    .forEach(training -> training.getExercise().forEach(exercise -> exercise.setDeleted(true)));

            user.setDeleted(true);

            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Usuário deletado com sucesso"
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
     * Método para resetar a senha do usuário
     *
     * @param   ResetPasswordDto resetPasswordDto
     * @param   Long id
     * @return  ResponseEntity
     */
    public ResponseEntity<?> resetPassword(ResetPasswordDto resetPasswordDto, Long id) {
        try {

            if (resetPasswordDto.getNewPassword() == null || resetPasswordDto.getOldPassword() == null) {
                throw new ParameterNullException("Parâmetros não informados");
            }

            String authenticatedUsername = ((UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getUsername();

            UserEntity user = userRepository.findByIdAndNotDeleted(id);

            if (user == null) throw new UserNotFoundException("Usuário não encontrado");

            // Verifico se o usuário autenticado é o mesmo que está tentando alterar a senha
            if (!authenticatedUsername.equals(user.getUsername())) {
                throw new UnauthorizedUserException("Usuário não autorizado a alterar a senha");
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // Verifico se a senha antiga é igual a senha cadastrada no banco
            if (!passwordEncoder.matches(resetPasswordDto.getOldPassword(), user.getPassword())) {
                throw new UnauthorizedUserException("Senha incorreta");
            }

            // Verifico se a nova senha é igual a confirmação de senha
            if (!resetPasswordDto.getNewPassword().equals(resetPasswordDto.getConfirmPassword())) {
                throw new PasswordNotMatchException("Senhas não conferem");
            }

            // Criptografo a nova senha e salvo no banco
            String hashedNewPassword = new BCryptPasswordEncoder().encode(resetPasswordDto.getNewPassword());
            user.setPassword(hashedNewPassword);

            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Senha alterada com sucesso"
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
     * Método para enviar email de recuperação de senha
     *
     * @param   String email
     * @return  ResponseEntity
     */
    public ResponseEntity<?> sendEmailForgotPasswordRequest(String email) {
        try {

            if (email == null) throw new ParameterNullException("Email não informado");

            UserEntity user = userRepository.findByEmail(email);

            if (user == null) throw new UserNotFoundException("Usuário não encontrado");

            // Gero um código de verificação
            String generatedCode = UUID.randomUUID().toString();

            // Salvo o código de verificação no banco
            user.getVerificationCode().setCode(generatedCode);

            verificationCodeRepository.save(user.getVerificationCode());

            // Gero um Json com o email do usuário e o código de verificação
            String json = "{\"email\": \"" + user.getEmail() + "\", \"uuid\": \"" + UUID.randomUUID() + "\"}";

            // Codifico o Json em Base64
            String base64Encoded = Base64.getEncoder().encodeToString(json.getBytes());

            EmailRequestDto emailRequest = new EmailRequestDto();

            // Configuro o email
            emailRequest.setTo(user.getEmail());
            emailRequest.setSubject("Recuperação de senha");
            emailRequest.setText("Clique aqui para redefinir sua senha: http://localhost:3000/reset-password?param=" + base64Encoded);

            // Envio o email
            emailService.sendEmail(emailRequest);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Código de verificação enviado para o email"
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

    public ResponseEntity<?> resetPasswordForgot(ResetPasswordForgotDto resetPasswordForgotDto) {
        try {

            if (resetPasswordForgotDto.getEmail() == null) throw new ParameterNullException("Email não informado");

            if (resetPasswordForgotDto.getNewPassword() == null) throw new ParameterNullException("Senha não informada");

            if (resetPasswordForgotDto.getConfirmPassword() == null) throw new ParameterNullException("Confirmação de senha não informada");

            if (resetPasswordForgotDto.getCode() == null) throw new ParameterNullException("Código de verificação não informado");

            UserEntity user = userRepository.findByEmail(resetPasswordForgotDto.getEmail());

            if (user == null) throw new UserNotFoundException("Usuário não encontrado");

            // Verifico se a nova senha é igual a confirmação de senha
            if (!resetPasswordForgotDto.getNewPassword().equals(resetPasswordForgotDto.getConfirmPassword())) {
                throw new PasswordNotMatchException("Senhas não conferem");
            }

            // verifico se o código de verificação é igual ao código salvo no banco
            if (!user.getVerificationCode().getCode().equals(resetPasswordForgotDto.getCode())) {
                throw new UnauthorizedUserException("Código de verificação inválido");
            }

            String hashedNewPassword = new BCryptPasswordEncoder().encode(resetPasswordForgotDto.getNewPassword());

            user.setPassword(hashedNewPassword);
            user.getVerificationCode().setCode(null);
            verificationCodeRepository.save(user.getVerificationCode());
            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Senha alterada com sucesso"
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
