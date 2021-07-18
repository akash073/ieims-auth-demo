# Backend API 2

This is a back-end API server written in Spring Boot that demonstrates
how to integrate authentication in the IEIMS project. It is configured
with the bearer-only client access type in Keycloak. That is, this
application will not participate in the login flow. It will only
check the access token sent from other clients and verify it with the
public key obtained from Keycloak.

This server gets requests from `api-1`. The requests are authorized
using the JWT tokens relayed from the front-end client.

## Demo Setup

This application has the following API endpoints:

1. `/upstream/admin/hello` - requires `UP_ADMIN` role defined on
   the `api-2` client.

## How It Was Made

We generated a started using `starter.spring.io` that only included
Spring Web and Spring Security. Then added the Keycloak Spring Boot
starter in the `pom.xml`

## Build and Run
```shell
./mvnw spring-boot:run
```

The app runs on port `9090`.

## Demonstration

Run `api-1` and this app, `api-2`. Then use `app-1` or
`app-2` to send requests to it by pressing the `Send * Upstream Hello`
button.
