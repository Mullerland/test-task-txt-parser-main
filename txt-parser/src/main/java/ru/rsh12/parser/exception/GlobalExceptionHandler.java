package ru.rsh12.parser.exception;
/*
 * Date: 09.02.2022
 * Time: 8:39 AM
 * */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(DuplicateKeyException ex) {
        var error = new ErrorResponse.Builder()
                .status(ex.getHttpStatus())
                .error(ex.getError())
                .message(ex.getMessage()).build();

        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatus()));
    }

}
