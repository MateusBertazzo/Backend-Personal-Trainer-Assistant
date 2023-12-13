package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.repositorys.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class User {

    private final UserRepository userRepository;

    public User(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity userEntity) {


        String hashedPassword = new BCryptPasswordEncoder().encode(userEntity.getPassword());
        userEntity.setPassword(hashedPassword);
        return userRepository.save(userEntity);
    }
}
