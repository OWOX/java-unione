package com.owox.unione.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class Webhook extends WebhookDescription {

    public enum EventFormat {json_post}

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private EventFormat eventFormat;

    @Getter
    @Setter
    private int maxParallel = 1;

    @Getter
    @Setter
    private WebhookEventsDescription events;

    public Webhook(String url) {
        super(url);
    }

}
