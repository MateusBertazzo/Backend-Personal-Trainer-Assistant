package br.com.apppersonal.apppersonal.exceptions;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException() {
        super("Unauthorized user");
    }
    public UnauthorizedUserException(String message) {
        super(message);
    }
}
