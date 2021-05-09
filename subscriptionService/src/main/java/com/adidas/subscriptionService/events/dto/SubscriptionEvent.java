package com.adidas.subscriptionService.events.dto;

import com.adidas.subscriptionService.model.Subscription;
import org.springframework.context.ApplicationEvent;

public class SubscriptionEvent extends ApplicationEvent {
    private Subscription subscription;
    private EventType eventType;

    public SubscriptionEvent(Object source, Subscription subscription, EventType eventType) {
        super(source);
        this.subscription = subscription;
        this.eventType = eventType;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public EventType getEventType() {
        return eventType;
    }

    public static SubscriptionMessage toMessage(SubscriptionEvent event) {
        return SubscriptionMessage.builder()
                .subscription(event.getSubscription())
                .eventType(event.eventType)
                .build();
    }
}
