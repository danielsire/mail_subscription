package com.adidas.mailService.consumer;

import com.adidas.mailService.dto.SubscriptionMessage;
import com.adidas.mailService.fakeMail.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionConsumer implements RabbitListenerConfigurer {

    @Autowired
    private EmailService emailService;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) { }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(SubscriptionMessage message) {
        emailService.sendMail(message);
    }

}
