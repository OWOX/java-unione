package com.owox.unione.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Template extends TemplateDescription {

    private String name;
    private String subject;
    private TemplateEngine templateEngine;
    private Map<String, Object> globalSubstitutions;
    private String fromEmail;
    private String fromName;
    private Map<String, String> headers;
    private MessageBody body;
    private List<Attachment> attachments;
    private List<Attachment> inlineAttachments;
    private Map<String, String> options;
    private String created;
    private long userId;
}
