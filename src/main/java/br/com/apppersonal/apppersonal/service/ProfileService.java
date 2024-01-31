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

    public ResponseEntity<?> updateProfile(Long id, ProfileDto profileDto) {
        if (id == null) throw new ParameterNullException();
        if (profileDto == null) throw new ParameterNullException();

        try {
            String authenticatedUsername = ((UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getUsername();

            ProfileEntity profileEntity = profileRepository.findByIdAndNotDeleted(id);

            if (profileEntity == null) throw new NotFoundProfileException();

            if (profileEntity.getUser().getDeleted()) throw new UpdateProfileException("Usuário deletado");

            if (!authenticatedUsername.equals(profileEntity.getUser().getUsername())) {
                throw new UnauthorizedProfileUpdateException();
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

    public ResponseEntity<?> getAllProfiles() {

        try {
            List<ProfileEntity> profileEntityList = profileRepository.findAllNotDeleted();

            if (profileEntityList.isEmpty()) {
                throw new NotFoundProfileException();
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

    private UserProfileDto convertToDTO(ProfileEntity profileEntity) {
        if (profileEntity == null) {
            throw new ParameterNullException();
        }

        UserProfileDto profileDTO = new UserProfileDto();
        UserEntity userEntity = profileEntity.getUser();

        profileDTO.setId(profileEntity.getId());
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

    public ResponseEntity<?> getProfileById(Long id) {
        try {
            if (id == null) throw new ParameterNullException("Identificar do usuário não informado");

            ProfileEntity profile = profileRepository.findByIdAndNotDeleted(id);

            if (profile == null) throw new NotFoundProfileException("Perfil Deletado");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Perfil retornado com sucesso",
                                    convertToDTO(profile)
                            )
                    );

        } catch (ParameterNullException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    false,
                                    e.getMessage()
                            )
                    );
        }  catch (NotFoundProfileException e) {
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
