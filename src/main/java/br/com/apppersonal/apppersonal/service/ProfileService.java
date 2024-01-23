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
import org.apache.coyote.Response;
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

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void updateProfile(Long id, ProfileDto profileDto) {
        if (id == null) throw new ParameterNullException();
        if (profileDto == null) throw new ParameterNullException();

        try {
            String authenticatedUsername = ((UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getUsername();

            ProfileEntity profileEntity = profileRepository.findById(id)
                    .orElseThrow(NotFoundProfileException::new);

            if (!authenticatedUsername.equals(profileEntity.getUser().getUsername())) {
                throw new UnauthorizedProfileUpdateException();
            }

            profileEntity.setFoto(profileDto.getFoto());
            profileEntity.setNumeroTelefone(profileDto.getNumeroTelefone());
            profileEntity.setObservacao(profileDto.getObservacao());
            profileEntity.setObjetivo(profileDto.getObjetivo());

            profileRepository.save(profileEntity);
        } catch (UpdateProfileException e) {
            throw new UpdateProfileException();
        }
    }

    public ResponseEntity<?> getAllProfiles() {

        try {
            List<ProfileEntity> profileEntityList = profileRepository.findAll();

            if (profileEntityList.isEmpty()) {
                throw new NotFoundProfileException();
            }


            var listProfile = profileEntityList.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(listProfile);

        } catch (NotFoundProfileException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public UserProfileDto convertToDTO(ProfileEntity profileEntity) {
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

    public UserProfileDto getProfileById(Long id) {
        if (id == null) throw new ParameterNullException();

        ProfileEntity profile = profileRepository.findById(id).orElseThrow(NotFoundProfileException::new);

        return convertToDTO(profile);
    }
}
