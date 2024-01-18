package br.com.apppersonal.apppersonal.advices;

import br.com.apppersonal.apppersonal.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionsAdvice {
    @ExceptionHandler(CreateUserErrorException.class)
    public ResponseEntity<String> createUserErrorExceptionHandler(CreateUserErrorException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundExceptionHandler(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity<String> passwordIncorrectExceptionHandler(PasswordIncorrectException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundProfileException.class)
    public ResponseEntity<String> notFoundProfileExceptionHandler(NotFoundProfileException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UpdateProfileException.class)
    public ResponseEntity<String> updateProfileExceptionHandler(UpdateProfileException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedProfileUpdateException.class)
    public ResponseEntity<String> unauthorizedProfileUpdateExceptionHandler(UnauthorizedProfileUpdateException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundUserMetrics.class)
    public ResponseEntity<String> notFoundUserMetricsExceptionHandler(NotFoundUserMetrics e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UpdateUserMetricsException.class)
    public ResponseEntity<String> updateUserMetricsExceptionHandler(UpdateUserMetricsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ParameterNullException.class)
    public ResponseEntity<String> parameterNullExceptionHandler(ParameterNullException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ExercisesNotFound.class)
    public ResponseEntity<String> exercisesNotFoundHandler(ExercisesNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UserGaleryException.class)
    public ResponseEntity<String> userGaleryExceptionHandler(UserGaleryException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
