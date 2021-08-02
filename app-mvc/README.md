# MVC App

This is an MVC application built using Spring Boot. It uses the
Keycloak Java adapter to integrate with Keycloak. For security,
it uses Spring Security.

The Keycloak Java adapter can be integrated with a Java web
application directly, or through Spring Security. We are opting
for integrating using Spring Security, so we can take advantage
of Spring Security's additional features in the future.

The Keycloak adapter can be configured using `keycloak.json`,
or in a Spring Boot setting, can be configured to use Spring
Boot's native configuration system using `application.properties`
or `application.yml`. We have configured the adapter in this demo
using the Spring Boot native method.

## Demo Setup

The application has two pages: `/` and `/test`. The home page
does not require any authentication or authorization. The `/test`
page requires authentication.

## Keycloak Client Access Type

We configured this application in Keycloak as a `confidential`
client as it is a backend only application.

## How It Was Made

We generated a starter using `starter.spring.io` that only included
Spring Web and Spring Security. Then added the Keycloak Spring Boot
starter in the `pom.xml`

## Build and Run
```shell
./mvnw spring-boot:run
```

The app runs on port `5000`.

## Demonstration

Run this app. Then visit the home page at `/`. Then click on the
`Protected Page` link.

After logging in, run another frontend application like `app-1`.
You will see that you are logged in there, because of the SSO.

Now logout from this application. You will see `app-1` has noticed
the action and logged out the user as well.

In another run, logout from `app-1`. If a page of this application,
`app-mvc` is already open, nothing will happen in the browser
window. But look at the logging output. You will see that Keycloak
has sent a back-channel logout request to it, and the user's session
has been terminated. If you continue to work in this application,
you will notice that the user has logged out because of Single Log
Out.
