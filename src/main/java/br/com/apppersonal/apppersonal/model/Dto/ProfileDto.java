package br.com.apppersonal.apppersonal.model.Dto;

import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;

public class ProfileDto {
    private String photo;

    private String phoneNumber;

    private String observation;

    private String goal;

    public ProfileDto(ProfileDto profileDto) {
        this.photo = profileDto.getPhoto();
        this.phoneNumber = profileDto.getPhoneNumber();
        this.observation = profileDto.getObservation();
        this.goal = profileDto.getGoal();
    }

    public ProfileDto() {}

    // Getters
    public String getPhoto() {
        return photo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getObservation() {
        return observation;
    }

    public String getGoal() {
        return goal;
    }
}
