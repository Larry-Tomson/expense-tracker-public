package com.luhtom.expensetracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ CategoryNotFoundException.class })
    public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404
                .body(exception.getMessage());
    }

    @ExceptionHandler({ CategoryNameAlreadyExist.class })
    public ResponseEntity<?> handleCategoryNameAlreadyExist(CategoryNameAlreadyExist exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(exception.getMessage());
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}