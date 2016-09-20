package com.owox.unione.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Client extends Base {

    @Getter
    private String username;

    @Getter
    private String apiKey;

}
