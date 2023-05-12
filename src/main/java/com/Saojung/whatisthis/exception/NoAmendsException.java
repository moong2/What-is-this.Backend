package com.Saojung.whatisthis.exception;

public class NoAmendsException extends  RuntimeException{
    public NoAmendsException() {
        super();
    }

    public NoAmendsException(String message) {
        super(message);
    }

    public NoAmendsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAmendsException(Throwable cause) {
        super(cause);
    }

    protected NoAmendsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
