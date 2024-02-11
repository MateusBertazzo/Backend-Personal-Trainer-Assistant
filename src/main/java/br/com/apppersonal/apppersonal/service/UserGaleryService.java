package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.ParameterNullException;
import br.com.apppersonal.apppersonal.exceptions.UserGaleryException;
import br.com.apppersonal.apppersonal.exceptions.UserNotFoundException;
import br.com.apppersonal.apppersonal.model.Dto.GaleryDto;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserGaleryEntity;
import br.com.apppersonal.apppersonal.model.repositorys.UserGaleryRepository;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserGaleryService {
    private final UserGaleryRepository userGaleryRepository;

    private final UserService userService;

    @Autowired
    public UserGaleryService(UserGaleryRepository userGaleryRepository, UserService userService) {
        this.userGaleryRepository = userGaleryRepository;
        this.userService = userService;
    }

    /**
     * Método para salvar a foto do usuário
     *
     * @param   Long userId
     * @param   String urlFoto
     * @return  ResponseEntity
     */
    public ResponseEntity<?> postFoto(Long userId, String urlFoto) {

        try {

            if (userId == null || urlFoto == null) throw new ParameterNullException("Parâmetros não informados");

            UserEntity user = userService.getUserById(userId);

            UserGaleryEntity userGaleryEntity = new UserGaleryEntity();
            userGaleryEntity.setDataFoto(LocalDate.now());
            userGaleryEntity.setUrlFoto(urlFoto);
            userGaleryEntity.setUser(user);

            userGaleryRepository.save(userGaleryEntity);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Foto salva com sucesso!"
                            )
                    );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    false,
                                    e.getMessage()
                            )
                    );
        }
    }

    /**
     * Método para buscar as fotos do usuário pelo id
     *
     * @param   Long userId
     * @return  ResponseEntity
     */
    public ResponseEntity<?> getFotosById(Long userId) {
        try {
            if (userId == null) throw new ParameterNullException("Usuário não informado");

            List<UserGaleryEntity> userGalery = userGaleryRepository.findAllByUserId(userId);

            if (userGalery.isEmpty()) throw new UserNotFoundException("Fotos não encontradas para o usuário informado");

            var responseGalery = userGalery
                    .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Fotos encontradas com sucesso",
                                    responseGalery
                            )
                    );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    false,
                                    e.getMessage()
                            )
                    );
        }
    }

    /**
     * Método para converter uma entidade de galeria de usuário (UserGaleryEntity) em um objeto de transferência de dados de galeria de usuário (GaleryDto).
     *
     * @param   UserGaleryEntity userGaleryEntity
     * @return  GaleryDto
     */
    public GaleryDto convertToDTO(UserGaleryEntity userGaleryEntity) {

        GaleryDto userGalleryDTO = new GaleryDto();
        userGalleryDTO.setId(userGaleryEntity.getId());
        userGalleryDTO.setUserId(userGaleryEntity.getUser().getId());
        userGalleryDTO.setUrlFoto(userGaleryEntity.getUrlFoto());
        userGalleryDTO.setDataFoto(userGaleryEntity.getDataFoto());

        return userGalleryDTO;
    }
}
