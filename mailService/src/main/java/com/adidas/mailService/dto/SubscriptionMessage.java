package com.adidas.mailService.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
@RequiredArgsConstructor
public class SubscriptionMessage implements Serializable {
    private static final long serialVersionUID = 7740336574800060517L;
    @NonNull
    private Subscription subscription;
    @NonNull
    private EventType eventType;
}
