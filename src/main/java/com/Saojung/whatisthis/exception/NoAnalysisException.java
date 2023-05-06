package com.Saojung.whatisthis.exception;

public class NoAnalysisException extends RuntimeException{
    public NoAnalysisException() {
        super();
    }

    public NoAnalysisException(String message) {
        super(message);
    }

    public NoAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAnalysisException(Throwable cause) {
        super(cause);
    }

    protected NoAnalysisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
