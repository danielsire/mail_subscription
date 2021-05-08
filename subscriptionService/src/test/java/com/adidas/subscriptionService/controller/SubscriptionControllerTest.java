package com.adidas.subscriptionService.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.six2six.fixturefactory.Fixture;
import com.adidas.subscriptionService.controller.dto.SubscriptionRequest;
import com.adidas.subscriptionService.fixture.BaseTestWithFixture;
import com.adidas.subscriptionService.fixture.model.SubscriptionFixture;
import com.adidas.subscriptionService.model.Subscription;
import com.adidas.subscriptionService.service.SubscriptionService;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@WebMvcTest(SubscriptionController.class)
@RunWith(SpringRunner.class)
public class SubscriptionControllerTest extends BaseTestWithFixture {

    @Autowired
    private MockMvc mvc;

    @Autowired
    @InjectMocks
    private SubscriptionController controller;

    @MockBean
    private SubscriptionService service;

    private final String resource = "/api/subscription";

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddNewSubscriptionReturnOk() throws Exception {
        SubscriptionRequest request = Fixture.from(SubscriptionRequest.class).gimme(SubscriptionFixture.VALID_SUBSCRIPTION_TO_CANCEL);
        Subscription expected = SubscriptionRequest.toModel(request);

        Mockito.when(service.upsert(any(SubscriptionRequest.class))).thenReturn(expected);

       mvc.perform(post(resource)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8")
                .content(new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(expected)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is(expected.getEmail())))
                .andExpect(jsonPath("$.firstName", is(expected.getFirstName())))
                .andExpect(jsonPath("$.gender", is(expected.getGender())));
    }

    @Test
    public void shouldListAllSubscriptionsReturnOk() throws Exception {
        List<Subscription> expected = Fixture.from(Subscription.class).gimme(2, SubscriptionFixture.VALID_SUBSCRIPTION_DATA);
        Mockito.when(service.findAllOrderByEmail()).thenReturn(expected);

        mvc.perform(get(resource))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void shouldGetOneSubscriptionReturnOk() throws Exception {
        Subscription expected = Fixture.from(Subscription.class).gimme(SubscriptionFixture.VALID_SUBSCRIPTION_DATA);
        Optional<Subscription> optExpected = Optional.of(expected);
        Mockito.when(service.findByEmail(expected.getEmail())).thenReturn(optExpected);

        mvc.perform(get(resource + "/" + expected.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(expected.getEmail())))
                .andExpect(jsonPath("$.firstName", is(expected.getFirstName())))
                .andExpect(jsonPath("$.isActive", is(expected.getIsActive())));
    }

    @Test
    public void shouldCancelOneSubscriptionReturnOk() throws Exception {
        SubscriptionRequest request = Fixture.from(SubscriptionRequest.class).gimme(SubscriptionFixture.VALID_SUBSCRIPTION_TO_CANCEL);
        Subscription expected = SubscriptionRequest.toModel(request);
        Mockito.when(service.cancelSubscription(expected.getEmail())).thenReturn(expected);

        mvc.perform(put(resource + "/" + expected.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(expected.getEmail())))
                .andExpect(jsonPath("$.firstName", is(expected.getFirstName())))
                .andExpect(jsonPath("$.isActive", is(expected.getIsActive())));
    }
}
