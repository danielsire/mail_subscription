package com.adidas.subscriptionService.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String email;

    @Column(name = "fist_name")
    private String firstName;

    private String gender;

    @NonNull
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @NonNull
    @Column(name = "flag_consent")
    private Boolean flagConsent;

    @NonNull
    @Column(name = "newsletter_id")
    private String newsLetterId;

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    void preInsert() {
        if (this.createdAt == null)
            this.createdAt = new Date();

        if (this.updatedAt == null)
            this.updatedAt = new Date();
    }

    @PreUpdate
    void preUpdate() {
        if (this.updatedAt == null)
            this.updatedAt = new Date();
    }

}
