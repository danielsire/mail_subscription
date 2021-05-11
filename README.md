[![Build Status](https://travis-ci.org/danielsire/mail_subscription.svg?branch=main)](https://travis-ci.org/danielsire/mail_subscription)

# Mail Subscriber - Challenge

Project to implement a simple Subscription Backend API.
The project consists of three independent modules that communicate between each other.

Modules:
- **Public-Service**: Microservice with public REST API, exposing methods to deal with subscriptions, [follow for more](https://github.com/danielsire/mail_subscription/blob/main/publicService/README.md "follow for more").
- **Subscription-Service**: Microservice with secured REST API, exposing methods to deal with subscriptions, internal logic to database persistence and triggering email notifications, [follow for more](https://github.com/danielsire/mail_subscription/blob/main/subscriptionService/README.md "follow for more").
- **Mail-Service**: Microservice that listen to events to send email notifications, [follow for more](https://github.com/danielsire/mail_subscription/blob/main/mailService/README.md "follow for more").

## Solution Proposal

This is a Java project using:
- SpringBoot.
- PostgreSQL for persistence.
- H2 for persistence in Test environment.
- RabbitMQ for events.

The communication between **subscription-service** and **public-service** is made by *mTLS*, which the only API publicly exposed is the **public-service** API.

And **mail-service** is not reachable, it only listens to RabbitMQ messages.

## CI/CD Proposal

A very simple continuous build is configured on [Travis CI](https://travis-ci.org/ "Travis CI").

For a real world approach the proposal would be:

- Run unit tests
- Check code style (team defined)

*Build fails in case any step does not succeed.*

- Check code coverage

- Build docker images
- Integration tests
- Run integration tests in an environment closest to production

*Build fails in case any step does not succeed.*

- Release version
- Deploy to production

*Rollback to the previous version if any step does not succeed.*

## Running App

Run `mvn clean install`

Run `docker-compose up`

Tomcat will start on port *8080*

## Swagger

Access `http://localhost:8080/v2/api-docs`