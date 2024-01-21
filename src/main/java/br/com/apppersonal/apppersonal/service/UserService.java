package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.*;
import br.com.apppersonal.apppersonal.model.Dto.UserDto;
import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserMetricsEntity;
import br.com.apppersonal.apppersonal.model.repositorys.ProfileRepository;
import br.com.apppersonal.apppersonal.model.repositorys.UserMetricsRepository;
import br.com.apppersonal.apppersonal.model.repositorys.UserRepository;
import br.com.apppersonal.apppersonal.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserMetricsRepository userMetricsRepository;

    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository,
                       UserMetricsRepository userMetricsRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.userMetricsRepository = userMetricsRepository;
        this.emailService = emailService;
    }

    public void createUser(UserEntity userParameter) {
        if (userParameter == null) throw new ParameterNullException();

        try {
            UserEntity userEntity = new UserEntity();
            String hashedPassword = new BCryptPasswordEncoder().encode(userParameter.getPassword());
            userEntity.setPassword(hashedPassword);
            userEntity.setEmail(userParameter.getEmail());
            userEntity.setUsername(userParameter.getUsername());
            userEntity.setRole(Role.USER);

            UserEntity saveUser = userRepository.save(userEntity);

            ProfileEntity profileEntity = new ProfileEntity();
            UserMetricsEntity userMetricsEntity = new UserMetricsEntity();

            profileEntity.setUser(saveUser);
            userMetricsEntity.setUser(saveUser);

            profileRepository.save(profileEntity);
            userMetricsRepository.save(userMetricsEntity);
        } catch (Exception e) {
            throw new CreateUserErrorException();
        }
    }

    public UserEntity getUserById(Long id) {
        if (id == null) throw new ParameterNullException();

        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UnauthorizedUserException();
        }

        return user;
    }

    public void deleteUser(Long id) {
        if (id == null) throw new ParameterNullException();

        UserEntity user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
    }

    public void resetPasswordRequest(String email) {
        if (email == null || email.isEmpty()) {
            throw new ParameterNullException();
        }

        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }


        String verificationCode = UUID.randomUUID().toString();

//        Criar um campo na tabela UserEntity para armazenar o código de verificação
//        user.setVerificationCode(verificationCode);
        userRepository.save(user);


        sendVerificationCodeByEmail(user.getEmail(), verificationCode);
    }

    public void resetPassword(String email, String verificationCode, String newPassword) {
        if (email == null || email.isEmpty() || verificationCode == null || verificationCode.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            throw new ParameterNullException();
        }

        UserEntity user = userRepository.findByEmail(email);
//        if (user == null || !verificationCode.equals(user.getVerificationCode())) {
//            throw new InvalidVerificationCodeException();
//        }


        String hashedPassword = new BCryptPasswordEncoder().encode(newPassword);
        user.setPassword(hashedPassword);
//      Criar um campo na tabela UserEntity para armazenar o código de verificação
//        user.setVerificationCode(null); // Limpar o código de verificação após a redefinição da senha
        userRepository.save(user);
    }

    private void sendVerificationCodeByEmail(String email, String verificationCode) {
        // Lógica para enviar o código de verificação por e-mail usando a classe EmailService
        String subject = "Código de Verificação - Redefinição de Senha";
        String message = "Seu código de verificação é: " + verificationCode;
        emailService.sendEmail(email, subject, message);
    }
}
