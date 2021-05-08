package com.adidas.publicService.dto;

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
}