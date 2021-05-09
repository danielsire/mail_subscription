package com.adidas.subscriptionService.events.dto;

import com.adidas.subscriptionService.model.Subscription;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class SubscriptionMessage implements Serializable {

    private static final long serialVersionUID = -7422705236933303262L;
    @NonNull
    private Subscription subscription;
    @NonNull
    private EventType eventType;
}

