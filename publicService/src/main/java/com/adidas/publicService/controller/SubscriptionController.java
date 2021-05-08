package com.adidas.publicService.controller;

import com.adidas.publicService.client.SubscriptionClient;
import com.adidas.publicService.dto.SubscriptionRequest;
import com.adidas.publicService.dto.SubscriptionResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionClient subscriptionService;

    @PostMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Create New Subscription")
    public ResponseEntity<SubscriptionResponse> create(@RequestBody final SubscriptionRequest subscription) {
        SubscriptionResponse newSubscription = subscriptionService.create(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSubscription);
    }

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="List all subscriptions ordered by email")
    public ResponseEntity<List<SubscriptionResponse>> findAll() {
        return ResponseEntity.ok(subscriptionService.findAll());
    }

    @GetMapping(value ="/{email}", produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Find a subscription by Email")
    public ResponseEntity<SubscriptionResponse> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(subscriptionService.findByEmail(email));
    }

    @PutMapping(value ="/{email}", produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Cancel a subscription by Email")
    public ResponseEntity<SubscriptionResponse> cancelSubscription(@PathVariable String email) {
        return ResponseEntity.ok(subscriptionService.cancelSubscription(email));
    }
}
