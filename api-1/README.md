# Backend API 1

This is a back-end API server written in Spring Boot that demonstrates
how to integrate authentication in the IEIMS project. It is configured
with the bearer-only client access type in Keycloak. That is, this
application will not participate in the login flow. It will only
check the access token sent from other clients and verify it with the
public key obtained from Keycloak.

## Demo Setup

This application has the following API endpoints:

1. `/admin/hello` - requires `ADMIN` client role in the `api-1` app.
2. `/user/hello`- requires `USER` client role in the `api-1` app.
3. `/admin/upHello` - requires `ADMIN` client role in the `api-1` app,
and `UP_ADMIN` client role in the `api-2` app.
   
The 3rd endpoint relays the request to `api-2` passing along the
access token received from the front-end client.
   
## Keycloak Adapter Configuration

Although we could use Spring Boot's native OAuth2 client library to
integrate with the IDP, using the Keycloak adapter offers some
advantages. We don't have to write code to map client roles into
application roles. The adapter also provides a convenient REST
template to call upstream services and pass along the bearer token.

The adapter can be configured either with a `keycloak.json`, or by
utilizing Spring Boot's native configuration file,
`application.(properties|yml|json)`. We chose to utilize the latter.

## How It Was Made

We generated a started using `starter.spring.io` that only included
Spring Web and Spring Security. Then added the Keycloak Spring Boot
starter in the `pom.xml`

## Build and Run
```shell
./mvnw spring-boot:run
```

The app runs on port `8080`.

## Demonstration

Run this app. Then use `app-1` or `app-2` to send requests to it
by pressing the `Send * Hello` buttons.
