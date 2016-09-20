package com.owox.unione.model.responses;

import com.owox.unione.model.Webhook;
import com.owox.unione.model.deserializers.WebhookDeserializer;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

public class WebhookResponse extends Response {

    @Getter
    @Setter
    @JsonDeserialize(using = WebhookDeserializer.class)
    Webhook object;
}
