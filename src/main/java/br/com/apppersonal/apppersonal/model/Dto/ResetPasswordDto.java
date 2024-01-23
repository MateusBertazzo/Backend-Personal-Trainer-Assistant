package br.com.apppersonal.apppersonal.model.Dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String oldPassword;

    private String newPassword;

    private String confirmPassword;
}
