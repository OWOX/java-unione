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
public class MessageTransmission extends Base {

    private String templateId;
    private TemplateEngine templateEngine = TemplateEngine.simple;
    private String subject;
    private Map<String, Object> globalSubstitutions;
    private String fromEmail;
    private String fromName;
    private Map<String, String> headers;
    private MessageBody body;
    private List<Attachment> attachments;
    private List<Attachment> inlineAttachments;
    private Map<String, String> options;
    private List<String> tags;
    private List<Recipient> recipients;
}
