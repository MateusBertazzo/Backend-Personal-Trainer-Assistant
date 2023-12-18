package br.com.apppersonal.apppersonal.exceptions;

public class UpdateProfileException extends RuntimeException{
    public UpdateProfileException() {
        super("Erro ao atualizar o perfil");
    }
}
