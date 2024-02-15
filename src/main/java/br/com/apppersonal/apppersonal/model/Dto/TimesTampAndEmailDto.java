package br.com.apppersonal.apppersonal.model.Dto;

import lombok.Data;

@Data
public class TimesTampAndEmailDto {
    private String email;
    private Long timesTamp;

    public TimesTampAndEmailDto(String email, Long timesTamp) {
        this.email = email;
        this.timesTamp = timesTamp;
    }
}
