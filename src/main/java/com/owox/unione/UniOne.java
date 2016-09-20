package com.owox.unione;

import com.owox.unione.model.Client;
import com.owox.unione.resources.ResourceBalance;
import com.owox.unione.resources.ResourceWebhooks;

public class UniOne {


    private Client client;

    public ResourceBalance balance;

    public ResourceWebhooks webhooks;

    public UniOne(final String username, final String apiKey) {
        this.client = new Client(username, apiKey);
        this.balance = new ResourceBalance(client);
        this.webhooks = new ResourceWebhooks(client);
    }
}
