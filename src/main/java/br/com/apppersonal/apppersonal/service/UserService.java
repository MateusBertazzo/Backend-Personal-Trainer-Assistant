package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.CreateUserErrorException;
import br.com.apppersonal.apppersonal.exceptions.ParameterNullException;
import br.com.apppersonal.apppersonal.exceptions.PasswordIncorrectException;
import br.com.apppersonal.apppersonal.exceptions.UserNotFoundException;
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

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserMetricsRepository userMetricsRepository;

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository, UserMetricsRepository userMetricsRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.userMetricsRepository = userMetricsRepository;
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
            throw new UserNotFoundException();
        }

        return user;
    }

    public void deleteUser(Long id) {
        if (id == null) throw new ParameterNullException();

        UserEntity user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
    }
}
