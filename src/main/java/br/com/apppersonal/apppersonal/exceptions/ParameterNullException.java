package br.com.apppersonal.apppersonal.exceptions;

public class ParameterNullException extends RuntimeException {
    public ParameterNullException() {
        super("Parameter cannot be null");
    }
}
