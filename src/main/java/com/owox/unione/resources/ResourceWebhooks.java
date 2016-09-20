package com.owox.unione.resources;

import com.owox.unione.exception.UniOneException;
import com.owox.unione.model.Client;
import com.owox.unione.model.Webhook;
import com.owox.unione.model.WebhookDescription;
import com.owox.unione.model.responses.Response;
import com.owox.unione.model.responses.WebhookResponse;
import com.owox.unione.transport.RestConnection;

public class ResourceWebhooks extends AbstractResource {

    private static final String PATH = "/ru/transactional/api/v1/webhook";

    public ResourceWebhooks(Client client) {
        super(client);
    }

    public WebhookResponse get(final WebhookDescription webhookDescription) throws UniOneException {

        return Response.decode(new RestConnection(client).post(
                PATH + "/get.json",
                mixClientFieldsToJsonForObject(webhookDescription)
        ), WebhookResponse.class);
    }

    public WebhookResponse set(final Webhook webhook) throws UniOneException {

        return Response.decode(new RestConnection(client).post(
                PATH + "/set.json",
                mixClientFieldsToJsonForObject(webhook)
        ), WebhookResponse.class);
    }

    public Response delete(final WebhookDescription webhookDescription) throws UniOneException {

        return Response.decode(new RestConnection(client).post(
                PATH + "/delete.json",
                mixClientFieldsToJsonForObject(webhookDescription)
        ), Response.class);
    }
}
