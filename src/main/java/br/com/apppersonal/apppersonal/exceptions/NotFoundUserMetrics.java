package br.com.apppersonal.apppersonal.exceptions;

public class NotFoundUserMetrics extends RuntimeException{
    public NotFoundUserMetrics() {
        super("Medidas não encontradas para este usuario");
    }
    public NotFoundUserMetrics(String message) {
        super(message);
    }
}
