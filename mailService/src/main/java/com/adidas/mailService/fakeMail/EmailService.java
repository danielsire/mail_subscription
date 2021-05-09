package com.adidas.mailService.fakeMail;

import com.adidas.mailService.dto.Subscription;
import com.adidas.mailService.dto.SubscriptionMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    public void sendMail(SubscriptionMessage message) {
        switch (message.getEventType()) {
            case CREATE:
                createMessage(message.getSubscription());
                break;
            case CANCEL:
                cancelMessage(message.getSubscription());
                break;
        }
    }

    private void createMessage(Subscription subscription) {
        StringBuffer buffer =  new StringBuffer()
                .append("-------------------------------------------------------\n")
                .append("to: ").append(subscription.getEmail()).append("\n")
                .append("Subject: You're in. Welcome to adidas\n")
                .append("-------------------------------------------------------\n")
                .append("Thank you for signing up. The journey starts today!\n")
                .append("-------------------------------------------------------\n");

        System.out.println(buffer);
    }

    private void cancelMessage(Subscription subscription) {
        StringBuffer buffer =  new StringBuffer()
                .append("-------------------------------------------------------\n")
                .append("to: ").append(subscription.getEmail()).append("\n")
                .append("Subject: Notice\n")
                .append("-------------------------------------------------------\n")
                .append("YOU ARE UNSUBSCRIBED.\n")
                .append("-------------------------------------------------------\n");

        System.out.println(buffer);
    }
}
