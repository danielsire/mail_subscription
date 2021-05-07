package com.adidas.subscriptionService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Such Subscription")
public class SubscriptionNotFoundException extends RuntimeException {

    public SubscriptionNotFoundException() {
        super();
    }

    public SubscriptionNotFoundException(String message) {
        super(message);
    }

    public SubscriptionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubscriptionNotFoundException(Throwable cause) {
        super(cause);
    }
}
