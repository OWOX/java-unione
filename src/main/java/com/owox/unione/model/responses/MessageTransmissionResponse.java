package com.owox.unione.model.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MessageTransmissionResponse extends Response {

    private String jobId;
    private List<String> emails;
    private Map<String, String> failedEmails;
}
