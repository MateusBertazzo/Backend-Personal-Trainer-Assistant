package br.com.apppersonal.apppersonal.exceptions;

public class ExercisesNotFound extends RuntimeException {
    public ExercisesNotFound() {
        super("Exercise not found");
    }

    public ExercisesNotFound(String message) {
        super(message);
    }
}
