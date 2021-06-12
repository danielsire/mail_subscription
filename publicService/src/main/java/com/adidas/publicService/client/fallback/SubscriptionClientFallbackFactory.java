package com.adidas.publicService.client.fallback;

import com.adidas.publicService.client.SubscriptionClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionClientFallbackFactory implements FallbackFactory<SubscriptionClient> {

    @Override
    public SubscriptionClient create(Throwable throwable) {
        return new SubscriptionClientFallback(throwable);
    }
}
