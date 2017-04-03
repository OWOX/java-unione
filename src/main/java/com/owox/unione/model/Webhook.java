package com.owox.unione.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Webhook extends WebhookDescription {

    public enum EventFormat {json_post}

    private long id;
    private EventFormat eventFormat;
    private int maxParallel = 1;
    private int singleEvent = 0;
    private WebhookEventsDescription events;

    public Webhook(String url) {
        super(url);
    }
}
