package br.com.apppersonal.apppersonal.model.Dto;

import lombok.Data;

@Data
public class UserCreateDto {

    private String username;

    private String email;

    private String password;

    private String confirmPassword;
}
