package br.com.apppersonal.apppersonal.exceptions;

public class NotFoundUserMetrics extends RuntimeException{
    public NotFoundUserMetrics() {
        super("Metrics for this user not found");
    }
}
