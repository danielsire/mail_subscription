package com.adidas.subscriptionService.events.applicationEvents;

import com.adidas.subscriptionService.controller.advice.GlobalControllerExceptionHandler;
import com.adidas.subscriptionService.events.dto.SubscriptionEvent;
import com.adidas.subscriptionService.events.rabbitMQBroker.Broker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener implements ApplicationListener<SubscriptionEvent> {
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @Autowired
    private Broker broker;

    @Override
    public void onApplicationEvent(SubscriptionEvent subscriptionEvent) {
        logger.info(subscriptionEvent.getEventType() + " message sent for: " + subscriptionEvent.getSubscription().getEmail());

        broker.send(subscriptionEvent);
    }
}
