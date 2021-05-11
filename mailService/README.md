# Mail-Service

Microservice that listens to events to send email notifications.

**No real emails are sent, just logs simulating emails.*

## Running App

Run `mvn clean install`

Run `docker-compose run mail-service`

## Utilization

Since this module has no interface or API, to use it you need to send messages directly on RabbitMQ.

Go to http://localhost:15672/#/queues/%2F/email.queue

And under ***publish message*** tab, fill in:

- Payload:
```
{
   "subscription":{
      "id":3,
      "email":"customer3@test.com",
      "firstName":"Blake",
      "gender":"Gender Neutral",
      "dateOfBirth":"2001-07-07",
      "flagConsent":true,
      "newsLetterId":"er7gf490us",
      "isActive":false,
      "createdAt":1620468000000,
      "updatedAt":1620468000000
   },
   "eventType":"CANCEL"
}
```

- Properties
    - priority:	0
    - delivery_mode:	2
- Headers
    - content_encoding:	UTF-8
    - content_type:	application/json
	