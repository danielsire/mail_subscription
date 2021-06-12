package com.adidas.publicService.client;

import com.adidas.publicService.client.configuration.FeignCustomClientConfiguration;
import com.adidas.publicService.client.fallback.SubscriptionClientFallbackFactory;
import com.adidas.publicService.dto.SubscriptionRequest;
import com.adidas.publicService.dto.SubscriptionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "subscriptionService",
        url = "${feign.client.url}",
        configuration = FeignCustomClientConfiguration.class,
        fallbackFactory = SubscriptionClientFallbackFactory.class
)
public interface SubscriptionClient {

    @GetMapping(value = "/api/subscription/{email}", produces= MediaType.APPLICATION_JSON_VALUE)
    SubscriptionResponse findByEmail(@PathVariable("email") String email);

    @GetMapping(value = "/api/subscription", produces= MediaType.APPLICATION_JSON_VALUE)
    List<SubscriptionResponse> findAll();

    @PutMapping(value = "/api/subscription/{email}", produces= MediaType.APPLICATION_JSON_VALUE)
    SubscriptionResponse cancelSubscription(@PathVariable("email") String email);

    @PostMapping(value = "/api/subscription", produces= MediaType.APPLICATION_JSON_VALUE)
    SubscriptionResponse create(@RequestBody final SubscriptionRequest subscription);
}
