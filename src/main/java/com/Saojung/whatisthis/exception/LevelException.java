package com.Saojung.whatisthis.exception;

public class LevelException extends RuntimeException{
    public LevelException() {
        super();
    }

    public LevelException(String message) {
        super(message);
    }

    public LevelException(String message, Throwable cause) {
        super(message, cause);
    }

    public LevelException(Throwable cause) {
        super(cause);
    }

    protected LevelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
