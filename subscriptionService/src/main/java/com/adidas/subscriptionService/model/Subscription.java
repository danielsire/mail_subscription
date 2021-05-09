package com.adidas.subscriptionService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Subscription implements Serializable {

    private static final long serialVersionUID = -538251056758452156L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String email;

    @Column(name = "first_name")
    private String firstName;

    private String gender;

    @NonNull
    @Column(name = "date_of_birth")
    @JsonFormat(pattern="yyyy-MM-dd")
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
