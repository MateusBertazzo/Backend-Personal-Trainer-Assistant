package br.com.apppersonal.apppersonal.exceptions;

public class UpdateProfileException extends RuntimeException{
    public UpdateProfileException() {
        super("Error to update profile");
    }
}
