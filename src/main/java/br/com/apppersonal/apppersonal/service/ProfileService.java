package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.exceptions.NotFoundProfileException;
import br.com.apppersonal.apppersonal.exceptions.UpdateProfileException;
import br.com.apppersonal.apppersonal.model.Dto.ProfileDto;
import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.model.repositorys.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void updateProfile(Long id, ProfileDto profileDto) {
        try{
            ProfileEntity profileEntity = getProfileById(id);

            profileEntity.setFoto(profileDto.getFoto());
            profileEntity.setNumeroTelefone(profileDto.getNumeroTelefone());
            profileEntity.setObservacao(profileDto.getObservacao());
            profileEntity.setObjetivo(profileDto.getObjetivo());

            profileRepository.save(profileEntity);
        } catch (UpdateProfileException e) {
            throw new UpdateProfileException();
        }
    }

    public List<ProfileEntity> getAllProfiles() {

        List<ProfileEntity> profileEntityList = profileRepository.findAll();

        if (profileEntityList.isEmpty()) {
            throw new NotFoundProfileException();
        }
        return profileEntityList;
    }

    public ProfileEntity getProfileById(Long id) {
        return profileRepository.findById(id).orElseThrow(NotFoundProfileException::new);
    }
}
