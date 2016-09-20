package com.owox.unione.model.responses;


import com.owox.unione.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The response for the UniOne server, as returned by RestConnection
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Response extends Base {

    /**
     * The URI of the request
     */
    private String request;

    private int responseCode = -1;

    private String status;

    private String contentType;

    private String responseMessage;

    private String responseBody;

    public static <T extends Response> T decode(Response response, Class<T> classOfT) {

        T newResponse = null;

        try {

            // Make sure this is a JSON response before we try to decode with Jackson
            if (response.getContentType() != null && response.getContentType().toLowerCase().startsWith("application/json")) {
                newResponse = MAPPER.readValue(response.getResponseBody(), classOfT);
            } else {
                newResponse = MAPPER.readValue("{}", classOfT);
            }

        } catch (IOException e) {
            Logger.getLogger(Response.class.getName()).log(Level.SEVERE, "Json decode exception", e);
            throw new RuntimeException(e);
        }

        if (newResponse != null) {
            newResponse.setRequest(response.request);
            newResponse.setResponseCode(response.responseCode);
            newResponse.setContentType(response.contentType);
            newResponse.setResponseBody(response.responseBody);
            newResponse.setResponseMessage(response.responseMessage);
        }

        return newResponse;
    }


}
