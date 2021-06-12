package com.adidas.publicService.client.fallback;

import com.adidas.publicService.client.SubscriptionClient;
import com.adidas.publicService.dto.SubscriptionRequest;
import com.adidas.publicService.dto.SubscriptionResponse;
import feign.FeignException;

import java.util.List;

public class SubscriptionClientFallback implements SubscriptionClient {

    private final Throwable cause;

    public SubscriptionClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public SubscriptionResponse findByEmail(String email) {
        testCause();

        return null;
    }

    @Override
    public List<SubscriptionResponse> findAll() {
        testCause();
        return null;
    }

    @Override
    public SubscriptionResponse cancelSubscription(String email) {
        testCause();
        return null;
    }

    @Override
    public SubscriptionResponse create(SubscriptionRequest subscription) {
        testCause();
        return null;
    }

    private void testCause() {
        if (cause instanceof FeignException) {
            throw new RuntimeException();
        }
    }
}
