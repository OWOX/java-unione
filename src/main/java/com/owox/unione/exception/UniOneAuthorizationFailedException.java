
package com.owox.unione.exception;

public class UniOneAuthorizationFailedException extends UniOneException {

    private static final String MESSAGE = "Authorization failed. Check your API key.";

    private static final long serialVersionUID = 4464029537833838156L;

    private final String serverMessage;

    public UniOneAuthorizationFailedException() {
        super(MESSAGE);
        this.serverMessage = MESSAGE;
    }

    public UniOneAuthorizationFailedException(String serverMessage) {
        super(MESSAGE);
        this.serverMessage = serverMessage;
    }

    public String getServerMessage() {
        return this.serverMessage;
    }
}
