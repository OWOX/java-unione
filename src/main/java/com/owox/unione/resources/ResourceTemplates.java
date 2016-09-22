package com.owox.unione.resources;

import com.owox.unione.exception.UniOneException;
import com.owox.unione.model.Client;
import com.owox.unione.model.Template;
import com.owox.unione.model.TemplateDescription;
import com.owox.unione.model.responses.Response;
import com.owox.unione.model.responses.TemplateResponse;
import com.owox.unione.model.responses.TemplatesListResponse;
import com.owox.unione.transport.RestConnection;

import java.util.HashMap;

public class ResourceTemplates extends AbstractResource {

    private static final String PATH = "/ru/transactional/api/v1/template";

    public ResourceTemplates(Client client) {
        super(client);
    }


    public TemplateResponse get(final TemplateDescription templateDescription) throws UniOneException {

        return Response.decode(new RestConnection(client).post(
                PATH + "/get.json",
                mixClientFieldsToJsonForObject(templateDescription)
        ), TemplateResponse.class);
    }


    public TemplatesListResponse list() throws UniOneException {
        return list(50, 0);
    }


    public TemplatesListResponse list(final int limit, final int offset) throws UniOneException {

        return Response.decode(new RestConnection(client).post(
                PATH + "/list.json",
                mixClientFieldsToJsonForObject(new HashMap<String, Object>() {{
                    put("limit", limit);
                    put("offset", offset);
                }})
        ), TemplatesListResponse.class);
    }


    public TemplateResponse set(final Template template) throws UniOneException {

        return Response.decode(new RestConnection(client).post(
                PATH + "/set.json",
                mixClientFieldsToJsonForObject(new HashMap<String, Object>() {{
                    put("template", template);
                }})
        ), TemplateResponse.class);
    }


    public Response delete(final TemplateDescription templateDescription) throws UniOneException {

        return Response.decode(new RestConnection(client).post(
                PATH + "/delete.json",
                mixClientFieldsToJsonForObject(templateDescription)
        ), Response.class);
    }

}
