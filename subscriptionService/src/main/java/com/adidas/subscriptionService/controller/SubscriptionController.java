package com.adidas.subscriptionService.controller;

import com.adidas.subscriptionService.exceptions.SubscriptionNotFoundException;
import com.adidas.subscriptionService.model.Subscription;
import com.adidas.subscriptionService.service.SubscriptionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    @PostMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Create New Subscription")
    public ResponseEntity<Subscription> create(@RequestBody final Subscription subscription) {
        return ResponseEntity.ok(service.upsert(subscription));
    }

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="List all subscriptions ordered by email")
    public ResponseEntity<List<Subscription>> findAll() {
        return ResponseEntity.ok(service.findAllOrderByEmail());
    }

    @GetMapping(value ="/{email}", produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Find a subscription by Email")
    public ResponseEntity<Subscription> findByEmail(@PathVariable String email) {
        Optional<Subscription> subscription = service.findByEmail(email);
        if (!subscription.isPresent()) {
            throw new SubscriptionNotFoundException("Subscription for " + email);
        }
        return ResponseEntity.ok(subscription.get());
    }

    @PutMapping(value ="/{email}", produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Cancel a subscription by Email")
    public ResponseEntity<Subscription> cancelSubscription(@PathVariable String email) {
        return ResponseEntity.ok(service.cancelSubscription(email));
    }

}
