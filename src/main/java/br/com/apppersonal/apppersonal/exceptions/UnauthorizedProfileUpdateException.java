package br.com.apppersonal.apppersonal.exceptions;

public class UnauthorizedProfileUpdateException extends RuntimeException{
    public UnauthorizedProfileUpdateException() {
        super("Unauthorized profile update");
    }
}
