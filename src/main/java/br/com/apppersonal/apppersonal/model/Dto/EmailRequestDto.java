package br.com.apppersonal.apppersonal.model.Dto;

import lombok.Data;

@Data
public class EmailRequestDto {
    private String to;
    private String subject;
    private String text;

    public EmailRequestDto(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public EmailRequestDto() {
    }
}
