package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.CreateUserErrorException;
import br.com.apppersonal.apppersonal.exceptions.PasswordIncorrectException;
import br.com.apppersonal.apppersonal.exceptions.UserNotFoundException;
import br.com.apppersonal.apppersonal.model.Dto.UserDto;
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
        try {
            String hashedPassword = new BCryptPasswordEncoder().encode(userEntity.getPassword());
            userEntity.setPassword(hashedPassword);
            userRepository.save(userEntity);
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
