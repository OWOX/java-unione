package com.owox.unione.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recipient extends Base {

    private String email;
    private Map<String, Object> substitutions;
    private Map<String, Object> metadata;
}
