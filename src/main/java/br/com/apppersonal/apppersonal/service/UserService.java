package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.*;
import br.com.apppersonal.apppersonal.model.Dto.ResetPasswordDto;
import br.com.apppersonal.apppersonal.model.Dto.UserDto;
import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserMetricsEntity;
import br.com.apppersonal.apppersonal.model.entitys.VerificationCodeEntity;
import br.com.apppersonal.apppersonal.model.repositorys.ProfileRepository;
import br.com.apppersonal.apppersonal.model.repositorys.UserMetricsRepository;
import br.com.apppersonal.apppersonal.model.repositorys.UserRepository;
import br.com.apppersonal.apppersonal.model.repositorys.VerificationCodeRepository;
import br.com.apppersonal.apppersonal.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserMetricsRepository userMetricsRepository;

    private final VerificationCodeRepository verificationCodeRepository;

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository,
                       UserMetricsRepository userMetricsRepository,
                       VerificationCodeRepository verificationCodeRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.userMetricsRepository = userMetricsRepository;
        this.verificationCodeRepository = verificationCodeRepository;
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
            VerificationCodeEntity verificationCodeEntity = new VerificationCodeEntity();

            verificationCodeEntity.setUser(saveUser);
            profileEntity.setUser(saveUser);
            userMetricsEntity.setUser(saveUser);

            profileRepository.save(profileEntity);
            userMetricsRepository.save(userMetricsEntity);
            verificationCodeRepository.save(verificationCodeEntity);

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
    public void resetPassword(ResetPasswordDto resetPasswordDto, Long id) {
        if (resetPasswordDto.getNewPassword() == null || resetPasswordDto.getOldPassword() == null) {
            throw new ParameterNullException();
        }

        String authenticatedUsername = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        UserEntity user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (!authenticatedUsername.equals(user.getUsername())) {
            throw new UnauthorizedUserException();
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(resetPasswordDto.getOldPassword(), user.getPassword())) {
            throw new UnauthorizedUserException();
        }

        if (!resetPasswordDto.getNewPassword().equals(resetPasswordDto.getConfirmPassword())) {
            throw new PasswordNotMatchException();
        }

        String hashedNewPassword = new BCryptPasswordEncoder().encode(resetPasswordDto.getNewPassword());
        user.setPassword(hashedNewPassword);

        userRepository.save(user);
    }
}
