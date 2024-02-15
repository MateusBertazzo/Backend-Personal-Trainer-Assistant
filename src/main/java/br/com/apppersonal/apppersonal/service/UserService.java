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
import br.com.apppersonal.apppersonal.utils.Base64Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserMetricsRepository userMetricsRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final Base64Code base64Code;

    @Autowired
    public UserService(
            UserRepository userRepository,
            ProfileRepository profileRepository,
            UserMetricsRepository userMetricsRepository,
            VerificationCodeRepository verificationCodeRepository,
            EmailService emailService,
            TokenService tokenService,
            Base64Code base64Code
    ) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.userMetricsRepository = userMetricsRepository;
        this.verificationCodeRepository = verificationCodeRepository;
        this.emailService = emailService;
        this.tokenService = tokenService;
        this.base64Code = base64Code;
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

            profileEntity.setUser(saveUser);
            userMetricsEntity.setUser(saveUser);

            profileRepository.save(profileEntity);
            userMetricsRepository.save(userMetricsEntity);

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

            VerificationCodeEntity verificationCodeEntity = verificationCodeRepository.findByUserId(user.getId());
            
            // Verificamos se já existe um código de verificação enviado.
            // Caso exista, devemos criar um código novo de verificação.
            if(verificationCodeEntity != null){
                verificationCodeEntity.setDeleted(true);
                verificationCodeRepository.save(verificationCodeEntity);
            }

            // Gero um código de verificação
            String generatedCode = tokenService.generateTokenToResetPassword(user.getEmail(), user.getPassword());

            // Salvo o código de verificação no banco
            VerificationCodeEntity newVerificationCodeEntity = new VerificationCodeEntity();
            newVerificationCodeEntity.setUser(user);
            newVerificationCodeEntity.setCode(generatedCode);
            verificationCodeRepository.save(newVerificationCodeEntity);

            // Gero um Json com o email do usuário e o código de verificação
            String tokenJson = "{\"email\": \"" + user.getEmail() + "\", \"code\": \"" + generatedCode + "\"}";

            // Codifico o Json em Base64
            String base64Encoded = base64Code.encode(tokenJson);

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

    /**
     * Método para resetar a senha do usuário no caso de ele ter esquecido a senha antiga
     *
     * @param   ResetPasswordForgotDto resetPasswordForgotDto (email, newPassword, confirmPassword, code)
     * @return  ResponseEntity
     */
    public ResponseEntity<?> resetPasswordForgot(ResetPasswordForgotDto resetPasswordForgotDto) {
        try {
            if (resetPasswordForgotDto.getEmail() == null) throw new ParameterNullException("Email não informado");

            if (resetPasswordForgotDto.getNewPassword() == null) throw new ParameterNullException("Senha não informada");

            if (resetPasswordForgotDto.getConfirmPassword() == null) throw new ParameterNullException("Confirmação de senha não informada");

            if (resetPasswordForgotDto.getCode() == null) throw new ParameterNullException("Código de verificação não informado");

            // Verifico se a nova senha é igual a confirmação de senha
            if (!resetPasswordForgotDto.getNewPassword().equals(resetPasswordForgotDto.getConfirmPassword())) {
                throw new PasswordNotMatchException("Senhas não conferem");
            }

            UserEntity user = userRepository.findByEmail(resetPasswordForgotDto.getEmail());

            if (user == null) throw new UserNotFoundException("Usuário não encontrado");

            VerificationCodeEntity verificationCodeEntity = verificationCodeRepository.findByUserId(user.getId());
            
            if(verificationCodeEntity == null) throw new UnauthorizedUserException("Código de verificação não encontrado.");
            
            // pego o verificaionCode do usuário que está no banco
            String domainToken = base64Code.decode(verificationCodeEntity.getCode());

            // pego o verificaionCode do usuário que está no corpo da requisição
            String clientToken = base64Code.decode(resetPasswordForgotDto.getCode());

            // faço a verificação se o token do corpo da requisição é igual ao token do banco
            if (!domainToken.equals(clientToken)) {
                throw new UnauthorizedUserException("Código de verificação inválido");
            }

            // pega o index 0 do token que é o timesTamp e converte para um date instant
            Instant createdAt = new Date(Long.parseLong(domainToken.split(":")[0])).toInstant();

            // pego o date atual
            Instant now = new Date().toInstant();

            // comparo se o timesTamp do token foi criado a + de 10 minutos e retorna um boolean
            Boolean isExpiredToken = createdAt.plus(Duration.ofMinutes(10)).isBefore(now);

            // faço a verificação se o token está expirado, caso for true é porque o token expirou
            if (isExpiredToken) {
                verificationCodeEntity.setDeleted(true);
                verificationCodeRepository.save(verificationCodeEntity);

                throw new UnauthorizedUserException("Código de verificação expirado");
            }

            // Criptografo a nova senha
            String hashedNewPassword = new BCryptPasswordEncoder().encode(resetPasswordForgotDto.getNewPassword());

            // Salvo a nova senha no banco
            user.setPassword(hashedNewPassword);

            userRepository.save(user);

            verificationCodeEntity.setDeleted(true);
            verificationCodeRepository.save(verificationCodeEntity);

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
