package com.adidas.subscriptionService.service;

import br.com.six2six.fixturefactory.Fixture;
import com.adidas.subscriptionService.controller.dto.SubscriptionRequest;
import com.adidas.subscriptionService.exceptions.SubscriptionNotFoundException;
import com.adidas.subscriptionService.fixture.BaseTestWithFixture;
import com.adidas.subscriptionService.fixture.model.SubscriptionFixture;
import com.adidas.subscriptionService.model.Subscription;
import com.adidas.subscriptionService.repository.SubscriptionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("tests")
public class SubscriptionServiceIT extends BaseTestWithFixture {

    @Autowired
    private SubscriptionService service;

    @Autowired
    private SubscriptionRepository repository;

    private List<Subscription> fixtureSubscriptions;

    @Before
    public void init() {
        repository.deleteAll();

        fixtureSubscriptions = Fixture.from(Subscription.class).gimme(2, SubscriptionFixture.VALID_SUBSCRIPTION_DATA_NO_ID);
        repository.saveAll(fixtureSubscriptions);
    }

    @Test
    public void shouldCreateSubscription() {
        SubscriptionRequest fixtureSubscription = Fixture.from(SubscriptionRequest.class).gimme(SubscriptionFixture.VALID_SUBSCRIPTION_TO_CANCEL);
        Subscription created = service.upsert(fixtureSubscription);

        Assert.assertNotNull(created);
        Assert.assertEquals(fixtureSubscription.getEmail(), created.getEmail());
        Assert.assertEquals(fixtureSubscription.getFirstName(), created.getFirstName());
        Assert.assertEquals(fixtureSubscription.getGender(), created.getGender());
        Assert.assertNotNull(created.getNewsLetterId());
        Assert.assertTrue(created.getIsActive());

        repository.delete(created);

        Optional<Subscription> deleted = service.findByEmail(created.getEmail());

        Assert.assertFalse(deleted.isPresent());
    }

    @Test
    public void shouldReturnSubscription() {
        Subscription expected = fixtureSubscriptions.get(0);

        Optional<Subscription> returned = service.findByEmail(expected.getEmail());

        Assert.assertTrue(returned.isPresent());
        Subscription returnedSubscription = returned.get();
        Assert.assertEquals(expected.getEmail(), returnedSubscription.getEmail());
        Assert.assertEquals(expected.getFirstName(), returnedSubscription.getFirstName());
        Assert.assertEquals(expected.getGender(), returnedSubscription.getGender());
        Assert.assertEquals(expected.getNewsLetterId(), returnedSubscription.getNewsLetterId());
    }

    @Test
    public void shouldReturnAllSubscriptions() {
        List<Subscription> subscriptions = service.findAllOrderByEmail();

        Assert.assertFalse(subscriptions.isEmpty());
        Assert.assertEquals(subscriptions.size(), fixtureSubscriptions.size());
        Assert.assertTrue(subscriptions.containsAll(fixtureSubscriptions));
    }

    @Test
    public void shouldReturnCancelSubscription() {
        Subscription expected = fixtureSubscriptions.get(1);

        Subscription returned = service.cancelSubscription(expected.getEmail());

        Assert.assertEquals(expected.getEmail(), returned.getEmail());
        Assert.assertEquals(expected.getFirstName(), returned.getFirstName());
        Assert.assertEquals(expected.getGender(), returned.getGender());
        Assert.assertEquals(expected.getNewsLetterId(), returned.getNewsLetterId());
        Assert.assertFalse(returned.getIsActive());
    }

    @Test(expected = SubscriptionNotFoundException.class)
    public void shouldThrowExceptionWhenCancelWithUnknownEmail() {
        service.cancelSubscription("unknown@mail.com");
    }
}
