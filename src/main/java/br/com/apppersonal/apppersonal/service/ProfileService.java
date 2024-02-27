package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.NotFoundProfileException;
import br.com.apppersonal.apppersonal.exceptions.ParameterNullException;
import br.com.apppersonal.apppersonal.exceptions.UnauthorizedProfileUpdateException;
import br.com.apppersonal.apppersonal.exceptions.UpdateProfileException;
import br.com.apppersonal.apppersonal.model.Dto.ProfileDto;
import br.com.apppersonal.apppersonal.model.Dto.UserProfileDto;
import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.repositorys.ProfileRepository;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    @JsonIgnore
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Método para atualizar o perfil do usuário
     *
     * @param   Long id
     * @param   ProfileDto profileDto
     * @return  ResponseEntity
     */
    public ResponseEntity<?> updateProfile(Long id, ProfileDto profileDto) {
        try {
            if (id == null) throw new UpdateProfileException("Identificador do perfil não informado");

            if (profileDto == null) throw new UpdateProfileException("Dados do perfil não informados");

            String authenticatedUsername = ((UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getUsername();

            ProfileEntity profileEntity = profileRepository.findByIdAndNotDeleted(id);

            if (profileEntity == null) throw new UpdateProfileException("Perfil não encontrado");

            if (profileEntity.getUser().getDeleted()) throw new UpdateProfileException("Usuário deletado");

            if (!authenticatedUsername.equals(profileEntity.getUser().getUsername())) {
                throw new UpdateProfileException("Usuário não autorizado a atualizar este perfil");
            }

            profileEntity.setFoto(profileDto.getFoto());
            profileEntity.setNumeroTelefone(profileDto.getNumeroTelefone());
            profileEntity.setObservacao(profileDto.getObservacao());
            profileEntity.setObjetivo(profileDto.getObjetivo());

            profileRepository.save(profileEntity);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Perfil atualizado com sucesso"
                            )
                    );

        } catch (UpdateProfileException e) {
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
     * Método para retornar todos os perfis
     *
     * @return  ResponseEntity
     */
    public ResponseEntity<?> getAllProfiles() {

        try {
            List<ProfileEntity> profileEntityList = profileRepository.findAllNotDeleted();

            if (profileEntityList.isEmpty()) {
                throw new NotFoundProfileException("Nenhum perfil encontrado");
            }


            var listProfile = profileEntityList.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Lista de perfis retornada com sucesso",
                                    listProfile
                            )
                    );

        } catch (NotFoundProfileException e) {
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
     * Método para converter uma entidade de perfil (ProfileEntity) em um objeto de transferência de dados de perfil (UserProfileDto).
     *
     * @param   ProfileEntity profileEntity
     * @return  ResponseEntity
     */
    private UserProfileDto convertToDTO(ProfileEntity profileEntity) {
        if (profileEntity == null) throw new ParameterNullException("Perfil não informado");

        UserProfileDto profileDTO = new UserProfileDto();
        UserEntity userEntity = profileEntity.getUser();

        profileDTO.setId(profileEntity.getId());
        profileDTO.setPersonalId(profileEntity.getUser().getPersonalTrainerId());
        profileDTO.setUserId(userEntity.getId());
        profileDTO.setUsername(userEntity.getUsername());
        profileDTO.setEmail(userEntity.getEmail());
        profileDTO.setRole(userEntity.getRole().name());
        profileDTO.setFoto(profileEntity.getFoto());
        profileDTO.setNumeroTelefone(profileEntity.getNumeroTelefone());
        profileDTO.setObservacao(profileEntity.getObservacao());
        profileDTO.setObjetivo(profileEntity.getObjetivo());

        return profileDTO;
    }

    /**
     * Método para retornar o perfil do usuário pelo id
     *
     * @param   Long id
     * @return  ResponseEntity
     */
    public ResponseEntity<?> getProfileById(Long id) {
        try {
            if (id == null) throw new ParameterNullException("Identificar do usuário não informado");

            ProfileEntity profile = profileRepository.findByIdAndNotDeleted(id);

            if (profile == null) throw new NotFoundProfileException("Perfil não encontrado");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Perfil retornado com sucesso",
                                    convertToDTO(profile)
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
}
