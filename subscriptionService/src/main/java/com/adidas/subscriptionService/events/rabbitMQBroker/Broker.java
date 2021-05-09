package com.adidas.subscriptionService.events.rabbitMQBroker;

import com.adidas.subscriptionService.events.dto.SubscriptionMessage;
import com.adidas.subscriptionService.events.dto.SubscriptionEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Broker {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.routingkey}")
    private String routingKey;

    public void send(SubscriptionEvent subscriptionEvent) {
        SubscriptionMessage message = SubscriptionEvent.toMessage(subscriptionEvent);

        rabbitTemplate.convertAndSend(exchange, routingKey, message, m -> {
            m.getMessageProperties().getHeaders().remove("__TypeId__");
            return m;
        });
    }
}
