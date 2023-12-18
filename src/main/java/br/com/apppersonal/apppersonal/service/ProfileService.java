package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.NotFoundProfileException;
import br.com.apppersonal.apppersonal.model.Dto.ProfileDto;
import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.model.repositorys.ProfileRepository;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void updateProfile(Long id, ProfileDto profileDto) {
        ProfileEntity profileEntity = getProfileById(id);

        profileEntity.setFoto(profileDto.getFoto());
        profileEntity.setNumeroTelefone(profileDto.getNumeroTelefone());
        profileEntity.setObservacao(profileDto.getObservacao());
        profileEntity.setObjetivo(profileDto.getObjetivo());

        profileRepository.save(profileEntity);
    }

    public List<ProfileEntity> getAllProfiles() {

        List<ProfileEntity> profileEntityList = profileRepository.findAll();
        int a = 0;
        return profileEntityList;
    }

    public ProfileEntity getProfileById(Long id) {
        return profileRepository.findById(id).orElseThrow(NotFoundProfileException::new);
    }
}
