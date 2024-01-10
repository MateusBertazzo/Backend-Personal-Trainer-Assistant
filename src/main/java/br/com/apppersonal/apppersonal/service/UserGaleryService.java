package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserGaleryEntity;
import br.com.apppersonal.apppersonal.model.repositorys.UserGaleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserGaleryService {
    private final UserGaleryRepository userGaleryRepository;

    private final UserService userService;

    @Autowired
    public UserGaleryService(UserGaleryRepository userGaleryRepository, UserService userService) {
        this.userGaleryRepository = userGaleryRepository;
        this.userService = userService;
    }

    public void postFoto(Long userId, String urlFoto) {
        try {
            UserEntity user = userService.getUserById(userId);
            UserGaleryEntity userGaleryEntity = new UserGaleryEntity();
            userGaleryEntity.setDataFoto(LocalDate.now());
            userGaleryEntity.setUrlFoto(urlFoto);
            userGaleryEntity.setUser(user);

            userGaleryRepository.save(userGaleryEntity);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao salvar foto");
        }
    }

    public List<UserGaleryEntity> getFotos(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Id do usuário não pode ser nulo");
        }

        List<UserGaleryEntity> userGalery = userGaleryRepository.findAllByUserId(userId);

        if (userGalery == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        return userGalery;
    }
}
