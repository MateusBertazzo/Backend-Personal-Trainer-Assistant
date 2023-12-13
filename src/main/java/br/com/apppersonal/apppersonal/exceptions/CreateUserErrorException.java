package br.com.apppersonal.apppersonal.exceptions;

public class CreateUserErrorException extends RuntimeException {
    public CreateUserErrorException() {
        super("Error to create user");
    }
}
