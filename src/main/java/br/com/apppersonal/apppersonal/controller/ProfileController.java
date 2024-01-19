package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.Dto.ProfileDto;
import br.com.apppersonal.apppersonal.model.Dto.UserProfileDto;
import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER') or hasRole('PERSONAL')")
    public void updateProfile(@PathVariable Long id, @RequestBody ProfileDto profileDto) {
        profileService.updateProfile(id, profileDto);
    }

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfileDto> getAllProfiles() {
       return profileService.getAllProfiles();
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('PERSONAL') or hasRole('ADMIN')")
    public UserProfileDto getProfileById(@PathVariable Long id) {
        return profileService.getProfileById(id);
    }
}
