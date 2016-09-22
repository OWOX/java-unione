package com.owox.unione.model;

import lombok.AllArgsConstructor;
import lombok.Getter;;

@Getter
@AllArgsConstructor
public class Client extends Base {

    private String username;
    private String apiKey;
}
