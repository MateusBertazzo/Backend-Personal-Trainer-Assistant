package br.com.apppersonal.apppersonal.exceptions;

public class PasswordIncorrectException extends RuntimeException{
    public PasswordIncorrectException() {
        super("Password incorrect");
    }
}
