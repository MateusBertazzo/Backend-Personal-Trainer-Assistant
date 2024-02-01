package br.com.apppersonal.apppersonal.exceptions;

public class NotFoundProfileException extends RuntimeException{
    public NotFoundProfileException() {
        super("Profile not found");
    }

    public NotFoundProfileException(String message) {
        super(message);
    }
}
