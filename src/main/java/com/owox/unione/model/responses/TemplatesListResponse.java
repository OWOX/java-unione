package com.owox.unione.model.responses;

import com.owox.unione.model.Template;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class TemplatesListResponse extends Response {

    @Getter
    @Setter
    List<Template> templates;
}
