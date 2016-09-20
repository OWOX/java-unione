package com.owox.unione.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class WebhookDescription {

    @Getter
    @Setter
    protected String url;

}
