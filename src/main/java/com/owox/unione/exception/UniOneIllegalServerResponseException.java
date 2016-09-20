
package com.owox.unione.exception;

public class UniOneIllegalServerResponseException extends UniOneException {


    private static final long serialVersionUID = 7748743708615269096L;

    public UniOneIllegalServerResponseException() {
    }

    public UniOneIllegalServerResponseException(String message) {
        super(message);
    }

    public UniOneIllegalServerResponseException(Throwable cause) {
        super(cause);
    }

    public UniOneIllegalServerResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
