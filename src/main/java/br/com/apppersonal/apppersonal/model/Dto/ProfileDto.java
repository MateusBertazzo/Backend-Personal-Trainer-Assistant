package br.com.apppersonal.apppersonal.model.Dto;

import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;

public class ProfileDto {
    private String foto;

    private String numeroTelefone;

    private String observacao;

    private String objetivo;

    public ProfileDto(ProfileDto profileDto) {
        this.foto = profileDto.getFoto();
        this.numeroTelefone = profileDto.getNumeroTelefone();
        this.observacao = profileDto.getObservacao();
        this.objetivo = profileDto.getObjetivo();
    }

    public ProfileDto() {}

    // Getters
    public String getFoto() {
        return foto;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public String getObservacao() {
        return observacao;
    }

    public String getObjetivo() {
        return objetivo;
    }
}
