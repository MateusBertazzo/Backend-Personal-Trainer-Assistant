package br.com.apppersonal.apppersonal.service;

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

    public UserEntity loginUser(UserEntity userEntity) {
        UserEntity user = userRepository.findByEmail(userEntity.getEmail());

        if (!(user != null) && new BCryptPasswordEncoder().matches(userEntity.getPassword(), user.getPassword())) {
            return null;
        }

        return user;
    }
}
