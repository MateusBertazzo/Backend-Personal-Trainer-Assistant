package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.CreateUserErrorException;
import br.com.apppersonal.apppersonal.exceptions.PasswordIncorrectException;
import br.com.apppersonal.apppersonal.exceptions.UserNotFoundException;
import br.com.apppersonal.apppersonal.model.Dto.UserDto;
import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserGaleryEntity;
import br.com.apppersonal.apppersonal.model.repositorys.ProfileRepository;
import br.com.apppersonal.apppersonal.model.repositorys.UserGaleryRepository;
import br.com.apppersonal.apppersonal.model.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserGaleryRepository userGaleryRepository;

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository, UserGaleryRepository userGaleryRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.userGaleryRepository = userGaleryRepository;
    }

    public void createUser(UserEntity userEntity) {
        try {
            String hashedPassword = new BCryptPasswordEncoder().encode(userEntity.getPassword());
            userEntity.setPassword(hashedPassword);
            UserEntity user = userRepository.save(userEntity);
            ProfileEntity profileEntity = new ProfileEntity();
            UserGaleryEntity userGaleryEntity = new UserGaleryEntity();
            userGaleryEntity.setUser(user);
            profileEntity.setUser(user);

            userGaleryRepository.save(userGaleryEntity);
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
