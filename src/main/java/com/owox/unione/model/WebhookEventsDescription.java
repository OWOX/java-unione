package com.owox.unione.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebhookEventsDescription {

    public enum EmailStatus {
        sent,
        delivered,
        opened,
        hard_bounced,
        soft_bounced,
        spam,
        clicked,
        unsubscribed
    }

    private EmailStatus[] emailStatus;
    private String[] spamBlock;
}
