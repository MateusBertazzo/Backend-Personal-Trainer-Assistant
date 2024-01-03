package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.CreateUserErrorException;
import br.com.apppersonal.apppersonal.exceptions.PasswordIncorrectException;
import br.com.apppersonal.apppersonal.exceptions.UserNotFoundException;
import br.com.apppersonal.apppersonal.model.Dto.UserDto;
import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.repositorys.ProfileRepository;
import br.com.apppersonal.apppersonal.model.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    public void createUser(UserEntity userEntity) {
        try {
            String hashedPassword = new BCryptPasswordEncoder().encode(userEntity.getPassword());
            userEntity.setPassword(hashedPassword);
            UserEntity user = userRepository.save(userEntity);
            ProfileEntity profileEntity = new ProfileEntity();
            profileEntity.setUser(user);

            profileRepository.save(profileEntity);
        } catch (Exception e) {
            throw new CreateUserErrorException();
        }
    }

    public UserEntity loginUser(UserDto userDto) {
        UserEntity user = userRepository.findByEmail(userDto.getEmail());
        if(user == null) {
            throw new UserNotFoundException();
        }

        if (!new BCryptPasswordEncoder().matches(userDto.getPassword(), user.getPassword())) {
            throw new PasswordIncorrectException();
        }

        UserEntity userNoPassword = new UserEntity
                (user.getId(), user.getName(), user.getEmail(), "", user.getRole());

        return userNoPassword;
    }
}
