package ru.rsh12.parser.exception;
/*
 * Date: 09.02.2022
 * Time: 8:38 AM
 * */

import org.springframework.http.HttpStatus;

public class DuplicateKeyException extends RuntimeException {

    private final String error;
    private final String message;
    private final HttpStatus httpStatus;

    public DuplicateKeyException(String message, HttpStatus httpStatus) {
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
