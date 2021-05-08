package com.adidas.publicService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SubscriptionResponse {

    private Long id;

    @NonNull
    private String email;

    private String firstName;

    private String gender;

    @NonNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;

    @NonNull
    private Boolean flagConsent;

    @NonNull
    private String newsLetterId;

    @NonNull
    private Boolean isActive;

    private Date createdAt;

    private Date updatedAt;
}
