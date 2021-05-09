package com.adidas.subscriptionService.service;

import com.adidas.subscriptionService.events.dto.EventType;
import com.adidas.subscriptionService.events.dto.SubscriptionEvent;
import com.adidas.subscriptionService.controller.dto.SubscriptionRequest;
import com.adidas.subscriptionService.exceptions.SubscriptionNotFoundException;
import com.adidas.subscriptionService.model.Subscription;
import com.adidas.subscriptionService.repository.SubscriptionRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository repository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public List<Subscription> findAllOrderByEmail() {
        return repository.findAllByOrderByEmailAsc();
    }

    public Optional<Subscription> findByEmail(String email) {
        return Optional.ofNullable(repository.findByEmail(email));
    }

    public Subscription upsert(SubscriptionRequest subscription) {
        //Add Newsletter Campaign - simulated one
        subscription.setNewsLetterId(getAlphanumericHash());
        subscription.setIsActive(true);

        Subscription upserted = repository.save(SubscriptionRequest.toModel(subscription));
        
        eventPublisher.publishEvent(new SubscriptionEvent(this, upserted, EventType.CREATE));
        
        return upserted;
    }

    public Subscription cancelSubscription(String email) {
        Optional<Subscription> subscription = findByEmail(email);
        if (!subscription.isPresent()) {
            throw new SubscriptionNotFoundException("Subscription for " + email);
        }
        subscription.get().setIsActive(false);

        Subscription canceled = repository.save(subscription.get());

        eventPublisher.publishEvent(new SubscriptionEvent(this, canceled, EventType.CANCEL));

        return repository.save(subscription.get());
    }

    private String getAlphanumericHash() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}