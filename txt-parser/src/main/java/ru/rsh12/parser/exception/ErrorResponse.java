package ru.rsh12.parser.exception;
/*
 * Date: 09.02.2022
 * Time: 8:39 AM
 * */

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.http.HttpStatus;

public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Yekaterinburg")
    private final Date timestamp;
    private final int status;
    private final String error;
    private final String message;

    public static class Builder {

        private Date timestamp = new Date();
        private int status = 500;
        private String error = "Internal server error";
        private String message = "Something went wrong";

        public Builder timestamp(Date val) {
            if (val != null) {
                timestamp = val;
            }
            return this;
        }

        public Builder status(HttpStatus httpStatus) {
            if (httpStatus != null) {
                status = httpStatus.value();
            }
            return this;
        }

        public Builder error(String val) {
            if (val != null) {
                error = val;
            }
            return this;
        }

        public Builder message(String val) {
            if (val != null) {
                message = val;
            }
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }

    private ErrorResponse(Builder builder) {
        timestamp = builder.timestamp;
        status = builder.status;
        error = builder.error;
        message = builder.message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

}
