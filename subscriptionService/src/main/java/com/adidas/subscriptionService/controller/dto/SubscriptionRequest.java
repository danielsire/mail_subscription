package com.adidas.subscriptionService.controller.dto;

import com.adidas.subscriptionService.model.Subscription;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SubscriptionRequest {
    @NonNull
    private String email;

    private String firstName;

    private String gender;

    @NonNull
    private Date dateOfBirth;

    @NonNull
    private Boolean flagConsent;

    @NonNull
    private String newsLetterId;

    @NonNull
    private Boolean isActive;

    public static Subscription toModel(SubscriptionRequest request) {
        Subscription newSubscription = new Subscription();
        newSubscription.setEmail(request.getEmail());
        newSubscription.setFirstName(request.getFirstName());
        newSubscription.setGender(request.getGender());
        newSubscription.setDateOfBirth(request.getDateOfBirth());
        newSubscription.setFlagConsent(request.getFlagConsent());
        newSubscription.setIsActive(request.getIsActive());
        newSubscription.setNewsLetterId(request.getNewsLetterId());

        return newSubscription;
    }
}
