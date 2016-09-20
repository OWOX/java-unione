package com.owox.unione.model.responses;

import lombok.Getter;
import lombok.Setter;

public class BalanceGetResponse extends Response {

    @Getter
    @Setter
    private float balance;

    @Getter
    @Setter
    private String currency;
}
