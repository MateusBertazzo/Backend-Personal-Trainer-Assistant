package br.com.apppersonal.apppersonal.exceptions;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException() {
        super("Password not match");
    }
    public PasswordNotMatchException(String message) {
        super(message);
    }
}
