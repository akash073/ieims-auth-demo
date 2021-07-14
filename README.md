# IEIMS Authentication Demo

This project demonstrates how to setup authentication in the IEIMS project
environment.

IEIMS will use OpenID Connect (OIDC) to provide user authentication for all
software components of the system. OIDC is a simple identity layer on top
of the OAuth2 protocol. IEIMS will also implement Single Sign On (SSO)
among all software components.

We will use [Keycloak](https://www.keycloak.org) as the Authentication Server
or Identity Provider (IDP). Any component in the system can use an OAuth2 or
ODIC library to integrate with the IDP. We recommend using an official
Keycloak Adapter appropiate for the software components framework. In
addition to making the integration easier, it provides some extra functionality
that is not part of the final standard of OIDC. The most important being
Single Log Out (SLO).

## Preparation

The devleoper should get familiar with the basics of OAuth, OIDC and JWT before
integrating an applicaiton in this environment. Please refer to the Reading
Material section at the end of this document as a starting point.

### Some Terminology

- **IDP:** Identity Provider. The Authentication Server, Keycloak in our case.
- **RP:** Relying Party. All applications that use the IDP for authenticating users.
- **Access Token:** JWT access token. Used to call back-end services. Has a short
	expiry time.
- **Refresh Token:** JWT refresh token. Sent in a refersh API call from the RP to
	the IDP to get a new pair of access and refresh tokens. Has a longer expiry
	time. Usually the same as the SSO session idle time.

## OpenID Client Access Types

There are three types of clients in OpenID:

- Public
- Confidential
- Beare Only

A public client does not need a shared secret with the IDP to initiate the login
process. It is suited for browser based Single Page Applications (SPAs) and mobile
applications.

A confidential client uses a shared secret with the IDP to initiate the login
process. It is more secure. But it can not be used by SPAs as there is no way to
secure the shared secret in such an application.

A bearer-only client never initiates a login process with the IDP.

### Client Types in IEIMS

Many components of IEIMS will be developed with separate front-end and back-ends.
The front-end using React or a similar technology. The back-end using Spring Boot,
PHP or similar technology. For such components, the front-end should be registered
in Keycloak as a public client. The back-end as a bearer-only client.

Components that are developed as a server side component only using Spring MVC, PHP
or similar frameworks, should register as a confidential client in Keycloak.

## Demo Components

This demo project has several folders:

1. keycloak: contains shell script and data for running the official keycloak docker image.
1. app-1: a front-end react application using `login-required` initialization option.
1. app-2: a front-end react application using `check-sso` initialization option.
1. api-1: a back-end spring-boot application with bearer-only access type. It has RESTful API endpoints.

Please refer to the README.md file in each folder to learn more about the components.

## Prerequisites

1. Docker must be installed
1. Node.js (14+) and npm (6+) must be installed
1. yarn (>= 1.22, < 2) is recommended

## Running The Demo

1. Go to the `keycloak` folder and run `./run.sh`. It will run keycloak with the pre-configured
	`development` realm.
1. Go to the `app-1` folder and run `yarn start`.
1. Go to the `app-2` folder and run `yarn start`.
1. Login in `app-1` with username: `razzak`, password: `razzak`.
1. Click the *Login* button in `app-2`
1. Click the *Logout* button in `app-2`

## Realm Setup

All clients, users, roles and other items live in a logical space named `realm` in Keycloak.
A single Keycloak installation can have mutliple realms defined. For this demo project,
we have pre-configured a realm named `development`.

### Roles

Keycloak has two types of user roles:

- Realm Role
- Client Role

Realm roles are global in the realm. Client roles are specific to clients. In the `development`
realm, we have configured two client roles for the `api-1` client:

- AMDIN
- USER

### Users

We have configured the following users in the `development` realm:

- razzak: has the client role ADMIN in client api-1
- ananta: has the client role USER in client api-1

The passwords of the usres are the same as their usernames.


## Reading Material
1. [Keycloak Documentation](https://www.keycloak.org/documentation)
1. [OAuth Basics](https://www.oauth.com)
1. [OpenID Connect FAQ](https://openid.net/connect/faq/)
