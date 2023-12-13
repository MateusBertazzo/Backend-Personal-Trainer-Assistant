package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.model.UserDto.UserDto;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.repositorys.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserEntity userEntity) {

        String hashedPassword = new BCryptPasswordEncoder().encode(userEntity.getPassword());
        userEntity.setPassword(hashedPassword);
        userRepository.save(userEntity);
    }

    public UserEntity loginUser(UserDto userDto) {
        UserEntity user = userRepository.findByEmail(userDto.getEmail());
        if(user == null) return null;

        if (!new BCryptPasswordEncoder().matches(userDto.getPassword(), user.getPassword())) {
            return null;
        }

        UserEntity userNoPassword = new UserEntity
                (user.getId(), user.getName(), user.getEmail(), "", user.getRole());

        return userNoPassword;
    }
}
