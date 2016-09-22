package com.owox.unione.model.responses;

import com.owox.unione.model.Template;
import lombok.Getter;
import lombok.Setter;

public class TemplateResponse extends Response {

    @Getter
    @Setter
    Template template;
}
