package br.com.apppersonal.apppersonal.exceptions;

public class UpdateUserMetricsException extends RuntimeException {
    public UpdateUserMetricsException() {
        super("Error updating data");
    }

    public UpdateUserMetricsException(String message) {
        super(message);
    }
}
