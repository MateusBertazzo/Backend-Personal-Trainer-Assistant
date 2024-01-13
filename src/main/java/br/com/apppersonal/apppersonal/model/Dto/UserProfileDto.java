package br.com.apppersonal.apppersonal.model.Dto;

import br.com.apppersonal.apppersonal.model.entitys.ProfileEntity;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import lombok.Data;

@Data
public class UserProfileDto {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String role;
    private String foto;
    private String numeroTelefone;
    private String observacao;
    private String objetivo;
}
