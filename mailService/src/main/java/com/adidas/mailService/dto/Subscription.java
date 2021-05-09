package com.adidas.mailService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Subscription implements Serializable {
    private static final long serialVersionUID = -2290898938797402753L;

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
