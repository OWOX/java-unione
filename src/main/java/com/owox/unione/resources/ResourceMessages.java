package com.owox.unione.resources;


import com.owox.unione.exception.UniOneException;
import com.owox.unione.model.Client;
import com.owox.unione.model.MessageTransmission;
import com.owox.unione.model.responses.MessageTransmissionResponse;
import com.owox.unione.model.responses.Response;
import com.owox.unione.transport.RestConnection;

import java.util.HashMap;

public class ResourceMessages extends AbstractResource {

    private static final String PATH = "/ru/transactional/api/v1/email";

    public ResourceMessages(Client client) {
        super(client);
    }

    public MessageTransmissionResponse send(final MessageTransmission messageTransmission) throws UniOneException {

        return Response.decode(new RestConnection(client).post(
                PATH + "/send.json",
                mixClientFieldsToJsonForObject(new HashMap<String, Object>() {{
                    put("message", messageTransmission);
                }})
        ), MessageTransmissionResponse.class);
    }
}
