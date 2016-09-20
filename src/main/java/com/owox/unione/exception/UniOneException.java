package com.owox.unione.exception;


public class UniOneException extends Exception {

    private static final long serialVersionUID = 8519989960515962081L;

    public UniOneException() {
    }

    public UniOneException(String message) {
        super(message);
    }

    public UniOneException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniOneException(Throwable cause) {
        super(cause);
    }

    public UniOneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
