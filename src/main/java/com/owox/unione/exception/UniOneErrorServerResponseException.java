
package com.owox.unione.exception;

import lombok.Getter;

public class UniOneErrorServerResponseException extends UniOneException {

    private static final long serialVersionUID = 761745089859390681L;

    @Getter
    private int responseCode = -1;

    public UniOneErrorServerResponseException(String message, int responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

}
