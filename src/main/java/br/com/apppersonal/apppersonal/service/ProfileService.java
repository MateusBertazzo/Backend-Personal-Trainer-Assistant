package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.NotFoundProfileException;
import br.com.apppersonal.apppersonal.exceptions.UpdateProfileException;
import br.com.apppersonal.apppersonal.model.Dto.ProfileDto;
import br.com.apppersonal.apppersonal.model.Dto.UserProfileDto;
import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.model.repositorys.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        try{
            ProfileEntity profileEntity = profileRepository.findById(id)
                    .orElseThrow(NotFoundProfileException::new);

            profileEntity.setFoto(profileDto.getFoto());
            profileEntity.setNumeroTelefone(profileDto.getNumeroTelefone());
            profileEntity.setObservacao(profileDto.getObservacao());
            profileEntity.setObjetivo(profileDto.getObjetivo());

            profileRepository.save(profileEntity);
        } catch (UpdateProfileException e) {
            throw new UpdateProfileException();
        }
    }

//    Busca todos os perfis
    public List<UserProfileDto> getAllProfiles() {

        List<ProfileEntity> profileEntityList = profileRepository.findAll();

        if (profileEntityList.isEmpty()) {
            throw new NotFoundProfileException();
        }

        return profileEntityList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

//    Converte um perfil para DTO
    public UserProfileDto convertToDTO(ProfileEntity profileEntity) {
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

//  Busca um perfil por ID
    public UserProfileDto getProfileById(Long id) {
        ProfileEntity profile = profileRepository.findById(id).orElseThrow(NotFoundProfileException::new);

        return convertToDTO(profile);
    }
}
