
package com.owox.unione.exception;

public class UniOneAccessForbiddenException extends UniOneException {

    private static final long serialVersionUID = -3415822422854163694L;

    private static final String MESSAGE = "API key has no required permission to perform this action.";

    private final String serverMessage;

    public UniOneAccessForbiddenException() {
        super(MESSAGE);
        this.serverMessage = MESSAGE;
    }

    public UniOneAccessForbiddenException(String serverMessage) {
        super(MESSAGE);
        this.serverMessage = serverMessage;
    }

    public String getServerMessage() {
        return this.serverMessage;
    }
}
