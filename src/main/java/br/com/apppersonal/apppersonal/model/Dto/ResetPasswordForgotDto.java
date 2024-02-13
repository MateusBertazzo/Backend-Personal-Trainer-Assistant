package br.com.apppersonal.apppersonal.model.Dto;

import lombok.Data;

@Data
public class ResetPasswordForgotDto {

    private String email;

    private String code;

    private String newPassword;

    private String confirmPassword;

    public ResetPasswordForgotDto() {
    }

    public ResetPasswordForgotDto(String email, String code, String newPassword, String confirmPassword) {
        this.email = email;
        this.code = code;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }
}
