package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Validation error: " + ex.getMessage());
    }


    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleValidationException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Validation error: " + ex.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleAllOtherException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Validation error: " + ex.getMessage());
    }
}
