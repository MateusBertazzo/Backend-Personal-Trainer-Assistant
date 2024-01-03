package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserGaleryEntity;
import org.springframework.stereotype.Service;
import br.com.apppersonal.apppersonal.model.repositorys.UserGaleryRepository;
import br.com.apppersonal.apppersonal.service.ProfileService;

import java.util.List;

@Service
public class UserGaleryService {
    private final UserGaleryRepository userGaleryRepository;

    public UserGaleryService(UserGaleryRepository userGaleryRepository) {
        this.userGaleryRepository = userGaleryRepository;
    }

    public void postFoto(Long userId, String urlFoto) {
        UserEntity userEntity = new UserEntity();
        UserGaleryEntity userGaleryEntity = new UserGaleryEntity();
        userGaleryEntity.setDataFoto(java.time.LocalDate.now());
        userGaleryEntity.setUser(userEntity);
        userEntity.setId(userId);
        userGaleryEntity.setUrlFoto(urlFoto);

        userGaleryRepository.save(userGaleryEntity);
    }

    public List<UserGaleryEntity> getFotos(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Id do usuário não pode ser nulo");
        }

        return userGaleryRepository.findAllByUserId(userId);
    }
}
