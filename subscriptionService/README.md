# Subscription-Service

Microservice with secured REST API, exposing methods to deal with subscriptions, internal logic to database persistence and triggering email notifications.

## Running App

Run `mvn clean install`

Run `docker-compose run subscription-service`

Tomcat will start on port *8081*

## Authentication

This module is mainly accessed by public-service sice it has the mTLS certificates, however if you want to test it using Postman or similar, get the [certificate](https://github.com/danielsire/mail_subscription/blob/main/subscriptionService/src/main/resources/certificates/identity.p12 "certificate") to configure your REST client.