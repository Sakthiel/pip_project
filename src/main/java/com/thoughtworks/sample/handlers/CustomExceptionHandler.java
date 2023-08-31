package com.thoughtworks.sample.handlers;

import com.thoughtworks.sample.handlers.model.ErrorResponse;
import com.thoughtworks.sample.version.exceptions.VersionNotAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

import static java.util.Collections.singletonList;
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final ArrayList<String> emptyDetails = new ArrayList<>();
    @ExceptionHandler(VersionNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleVersionNotFoundException(VersionNotAvailableException ex) {
        ErrorResponse error = new ErrorResponse("Version not found", singletonList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
