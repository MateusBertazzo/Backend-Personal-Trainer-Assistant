package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.CreateUserErrorException;
import br.com.apppersonal.apppersonal.exceptions.PasswordIncorrectException;
import br.com.apppersonal.apppersonal.exceptions.UserNotFoundException;
import br.com.apppersonal.apppersonal.model.Dto.UserDto;
import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserMetricsEntity;
import br.com.apppersonal.apppersonal.model.repositorys.ProfileRepository;
import br.com.apppersonal.apppersonal.model.repositorys.UserMetricsRepository;
import br.com.apppersonal.apppersonal.model.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    private final UserMetricsRepository userMetricsRepository;

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository, UserMetricsRepository userMetricsRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.userMetricsRepository = userMetricsRepository;
    }

    public void createUser(UserEntity userEntity) {
        if (userEntity == null) throw new RuntimeException("Usuário não pode ser vazio");

        try {
            String hashedPassword = new BCryptPasswordEncoder().encode(userEntity.getPassword());
            userEntity.setPassword(hashedPassword);
            UserEntity user = userRepository.save(userEntity);
            ProfileEntity profileEntity = new ProfileEntity();
            UserMetricsEntity userMetricsEntity = new UserMetricsEntity();
            profileEntity.setUser(user);
            userMetricsEntity.setUser(user);

            profileRepository.save(profileEntity);
            userMetricsRepository.save(userMetricsEntity);
        } catch (Exception e) {
            throw new CreateUserErrorException();
        }
    }

    public UserEntity loginUser(UserDto userDto) {
        if (userDto == null) throw new RuntimeException("Usuário não pode ser vazio");

        UserEntity user = userRepository.findByEmail(userDto.getEmail());
        if(user == null) {
            throw new UserNotFoundException();
        }

        if (!new BCryptPasswordEncoder().matches(userDto.getPassword(), user.getPassword())) {
            throw new PasswordIncorrectException();
        }

        return user;
    }

    public UserEntity getUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if(user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }
}
