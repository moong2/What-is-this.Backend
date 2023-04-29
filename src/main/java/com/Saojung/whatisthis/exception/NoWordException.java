package com.Saojung.whatisthis.exception;

public class NoWordException extends RuntimeException{
    public NoWordException() {
    }

    public NoWordException(String message) {
        super(message);
    }

    public NoWordException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoWordException(Throwable cause) {
        super(cause);
    }

    public NoWordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
