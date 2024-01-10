package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.Dto.ProfileDto;
import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(@PathVariable Long id, @RequestBody ProfileDto profileDto) {
        profileService.updateProfile(id, profileDto);
    }

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProfileEntity> getAllProfiles() {
       return profileService.getAllProfiles();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProfileEntity getProfileById(@PathVariable Long id) {
        return profileService.getProfileById(id);
    }
}
