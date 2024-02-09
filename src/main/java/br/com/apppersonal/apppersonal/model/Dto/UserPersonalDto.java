package br.com.apppersonal.apppersonal.model.Dto;

import lombok.Data;
import br.com.apppersonal.apppersonal.security.Role;

@Data
public class UserPersonalDto {
    private Long userId;
    private String username;
    private String email;
    private Role role;
    private String numeroTelefone;
        
    public UserPersonalDto() {
    }
        
    public UserPersonalDto(Long userId, String username, String email, Role role, String numeroTelefone) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.numeroTelefone = numeroTelefone;
    }

    public <R> UserPersonalDto(R collect) {
    }
}
