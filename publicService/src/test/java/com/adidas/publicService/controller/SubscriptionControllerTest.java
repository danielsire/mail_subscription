package com.adidas.publicService.controller;

import br.com.six2six.fixturefactory.Fixture;
import com.adidas.publicService.client.SubscriptionClient;
import com.adidas.publicService.dto.SubscriptionRequest;
import com.adidas.publicService.dto.SubscriptionResponse;
import com.adidas.publicService.fixture.BaseTestWithFixture;
import com.adidas.publicService.fixture.model.SubscriptionFixture;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubscriptionController.class)
@RunWith(SpringRunner.class)
public class SubscriptionControllerTest extends BaseTestWithFixture {

    @Autowired
    private MockMvc mvc;

    @Autowired
    @InjectMocks
    private SubscriptionController controller;

    @MockBean
    private SubscriptionClient service;

    private final String resource = "/api/subscription";

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddNewSubscriptionReturnOk() throws Exception {
        SubscriptionResponse expected = Fixture.from(SubscriptionResponse.class).gimme(SubscriptionFixture.VALID_SUBSCRIPTION_DATA);

        Mockito.when(service.create(any(SubscriptionRequest.class))).thenReturn(expected);

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
        List<SubscriptionResponse> expected = Fixture.from(SubscriptionResponse.class).gimme(2, SubscriptionFixture.VALID_SUBSCRIPTION_DATA);
        Mockito.when(service.findAll()).thenReturn(expected);

        mvc.perform(get(resource))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void shouldGetOneSubscriptionReturnOk() throws Exception {
        SubscriptionResponse expected = Fixture.from(SubscriptionResponse.class).gimme(SubscriptionFixture.VALID_SUBSCRIPTION_DATA);
        Mockito.when(service.findByEmail(expected.getEmail())).thenReturn(expected);

        mvc.perform(get(resource + "/" + expected.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(expected.getEmail())))
                .andExpect(jsonPath("$.firstName", is(expected.getFirstName())))
                .andExpect(jsonPath("$.isActive", is(expected.getIsActive())));
    }

    @Test
    public void shouldCancelOneSubscriptionReturnOk() throws Exception {
        SubscriptionResponse expected = Fixture.from(SubscriptionResponse.class).gimme(SubscriptionFixture.VALID_SUBSCRIPTION_DATA);
        Mockito.when(service.cancelSubscription(expected.getEmail())).thenReturn(expected);

        mvc.perform(put(resource + "/" + expected.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(expected.getEmail())))
                .andExpect(jsonPath("$.firstName", is(expected.getFirstName())))
                .andExpect(jsonPath("$.isActive", is(expected.getIsActive())));
    }

}
