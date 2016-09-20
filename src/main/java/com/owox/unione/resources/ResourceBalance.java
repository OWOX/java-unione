package com.owox.unione.resources;

import com.owox.unione.model.Client;
import com.owox.unione.exception.UniOneException;
import com.owox.unione.model.responses.BalanceGetResponse;
import com.owox.unione.model.responses.Response;
import com.owox.unione.transport.RestConnection;

public class ResourceBalance extends AbstractResource {

    public ResourceBalance(Client client) {
        super(client);
    }

    public BalanceGetResponse get() throws UniOneException {
        return Response.decode(
                new RestConnection(client).post("/ru/transactional/api/v1/balance.json", client.toJson()),
                BalanceGetResponse.class
        );
    }
}
