package br.com.apppersonal.apppersonal.exceptions;

public class NotFoundProfileException extends RuntimeException{
    public NotFoundProfileException() {
        super("Profile not found");
    }
}
