package com.workintech.s18d2.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<PlantErrorResponse> handleException(PlantException exception) {
        PlantErrorResponse plantErrorResponse = new PlantErrorResponse(exception.getHttpStatus().value(), exception.getMessage(), LocalDateTime.now());
        log.error("Plant exception occured: ", exception);
        return new ResponseEntity<>(plantErrorResponse, exception.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<PlantErrorResponse> handleException(HandlerMethodValidationException exception) {
        PlantErrorResponse plantErrorResponse = new PlantErrorResponse(HttpStatus.BAD_REQUEST.value(),
                Arrays.stream(Objects.requireNonNull(exception.getDetailMessageArguments())).findFirst().isPresent() ? Arrays.stream(exception.getDetailMessageArguments()).findFirst().get().toString() : "hata oluştu", LocalDateTime.now());
        log.error("Plant exception occured: ", exception);
        return new ResponseEntity<>(plantErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<PlantErrorResponse> handleException(Exception exception) {
        PlantErrorResponse plantErrorResponse = new PlantErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), LocalDateTime.now());
        log.error("Plant exception occured: ", exception);
        return new ResponseEntity<>(plantErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
