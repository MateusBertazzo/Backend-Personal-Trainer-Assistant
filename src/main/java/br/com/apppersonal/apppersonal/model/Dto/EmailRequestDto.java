package br.com.apppersonal.apppersonal.model.Dto;

import lombok.Data;

@Data
public class EmailRequestDto {

    private Long userId;
    private String to;
    private String subject = "Redefinição de senha";
    private String text;

    public EmailRequestDto(String to, String subject, String text, Long userId) {
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.userId = userId;
    }

    public EmailRequestDto() {
    }
}
