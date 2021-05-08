package com.example.tdstrialwork.services.support;

import com.example.tdstrialwork.helpers.Constants;
import io.reflectoring.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Error> handleConstraintViolationException(final ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Error().message(e.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<Error> handleNoSuchElementException(final NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error().message(Constants.ITEM_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    ResponseEntity<Error> handleNoSuchElementException(final HttpServerErrorException.InternalServerError e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error().message(e.getMessage()));
    }
}
