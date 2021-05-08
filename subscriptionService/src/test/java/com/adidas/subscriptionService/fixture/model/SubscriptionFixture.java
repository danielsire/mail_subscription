package com.adidas.subscriptionService.fixture.model;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.adidas.subscriptionService.controller.dto.SubscriptionRequest;
import com.adidas.subscriptionService.model.Subscription;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SubscriptionFixture implements TemplateLoader {

    private final String EMAIL1 = "customer1@mail.com";
    private final String EMAIL2 = "customer2@mail.com";
    private final String EMAIL3 = "customer3@mail.com";

    private final String FIRST_NAME1 = "customer1";
    private final String FIRST_NAME2 = "customer2";
    private final String FIRST_NAME3 = "customer3";

    private final String GENDER1 = "male";
    private final String GENDER2 = "female";
    private final String GENDER3 = "gender neutral";

    private final Date DATE_BIRTH1 = new GregorianCalendar(2000, Calendar.FEBRUARY, 2).getTime();
    private final Date DATE_BIRTH2 = new GregorianCalendar(1998, Calendar.AUGUST, 10).getTime();
    private final Date DATE_BIRTH3 = new GregorianCalendar(2002, Calendar.SEPTEMBER, 20).getTime();

    private final Boolean FLAG_CONSENT_TRUE = Boolean.TRUE;
    private final Boolean FLAG_CONSENT_FALSE = Boolean.FALSE;

    private final String NEWSLETTER_ID1 = "mfDlzqKcVu";
    private final String NEWSLETTER_ID2 = "ob23Sh45wM";
    private final String NEWSLETTER_ID3 = "UrLOxj5v21";

    private final Boolean IS_ACTIVE = Boolean.TRUE;
    private final Boolean IS_NOT_ACTIVE = Boolean.FALSE;

    public static final String VALID_SUBSCRIPTION_DATA = "validSubscriptionData";

    public static final String VALID_SUBSCRIPTION_DATA_NO_ID = "validSubscriptionDataNoId";

    public static final String VALID_SUBSCRIPTION_TO_CANCEL = "validSubscriptionToCancel";

    @Override
    public void load() {
        Fixture.of(Subscription.class).addTemplate(VALID_SUBSCRIPTION_DATA, new Rule() {
            {
                add("id", random(Long.class, range(1L, 5L)));
                add("email", uniqueRandom(EMAIL1, EMAIL2));
                add("firstName", uniqueRandom(FIRST_NAME1, FIRST_NAME2));
                add("gender", uniqueRandom(GENDER1, GENDER2));
                add("dateOfBirth", uniqueRandom(DATE_BIRTH1, DATE_BIRTH2));
                add("flagConsent", uniqueRandom(FLAG_CONSENT_TRUE, FLAG_CONSENT_FALSE));
                add("newsLetterId", uniqueRandom(NEWSLETTER_ID1, NEWSLETTER_ID2));
                add("isActive", IS_ACTIVE);
            }
        });

        Fixture.of(Subscription.class).addTemplate(VALID_SUBSCRIPTION_DATA_NO_ID, new Rule() {
            {
                add("email", uniqueRandom(EMAIL1, EMAIL2));
                add("firstName", uniqueRandom(FIRST_NAME1, FIRST_NAME2));
                add("gender", uniqueRandom(GENDER1, GENDER2));
                add("dateOfBirth", uniqueRandom(DATE_BIRTH1, DATE_BIRTH2));
                add("flagConsent", uniqueRandom(FLAG_CONSENT_TRUE, FLAG_CONSENT_FALSE));
                add("newsLetterId", uniqueRandom(NEWSLETTER_ID1, NEWSLETTER_ID2));
                add("isActive", IS_ACTIVE);
            }
        });

        Fixture.of(SubscriptionRequest.class).addTemplate(VALID_SUBSCRIPTION_TO_CANCEL, new Rule() {
            {
                add("email", EMAIL3);
                add("firstName", FIRST_NAME3);
                add("gender", GENDER3);
                add("dateOfBirth", DATE_BIRTH3);
                add("flagConsent", FLAG_CONSENT_TRUE);
                add("newsLetterId", NEWSLETTER_ID3);
                add("isActive", IS_NOT_ACTIVE);
            }
        });
    }
}
