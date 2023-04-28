package com.Saojung.whatisthis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CannotJoinException extends RuntimeException{
    public CannotJoinException() {
        super();
    }

    public CannotJoinException(String message) {
        super(message);
    }

    public CannotJoinException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotJoinException(Throwable cause) {
        super(cause);
    }

    protected CannotJoinException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
