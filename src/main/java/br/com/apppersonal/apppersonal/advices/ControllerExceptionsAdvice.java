package br.com.apppersonal.apppersonal.advices;

import br.com.apppersonal.apppersonal.exceptions.CreateUserErrorException;
import br.com.apppersonal.apppersonal.exceptions.PasswordIncorrectException;
import br.com.apppersonal.apppersonal.exceptions.UserNotFoundException;
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
}
