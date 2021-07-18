# Keycloak

This folder has some helpful scripts to run the official Keycloak docker
image with a preset realm and some user accounts.

## Prerequisits

Docker must be installed beofre running any script.

## A Few Words on Export/Import

The Keycloak admin interface has a realm export/import feature. It can
be used from the browser. But this feature does not include user accounts
in the export/import.

The Keycloak server has another feature of importing realms and users
during startup. That feature is using in our scripts.


## The Scripts

### `create.sh`

This script should be run first. It creates a container named `keycloak`
and bind mounts the `realm-data` directory to the container. It creates
and admin user in Keycloak with username: `admin` and password:
`admin`.

### `start.sh`

This script should be used for subsequent runs of the `keycloak`
container. The container is run in the foreground so you can see the logs.
Press Control-C to stop it.

### `export.sh`

If you make modifications to the realm or its users, run this script to
export the realm and user data to the `realm-data` directory. If you
fail to do this and the container is stopped for some reason, you will
lose your modifications. The container is stopped if you stop it manually,
stop the docker machine or shutdown your computer.

## The Realm

We have setup a realm named `development`. It defines four Keycloak
clients:

1. `app-1`: Front-end React app. Public access type.
1. `app-2`: Front-end React app. Public access type.
1. `api-1`: Back-end API server in Spring Boot. Bearer only access type.
1. `api-2`: Back-end API server in Spring Boot. Bearer only aceess type.

The two back-end applications have some *client roles* defined on them.

The realm is also configured with a *client scope* named `ieims`. Basically
it adds a few *user attributes* to the issued access tokens.

### Users

We have defined two users for the demo:

1. `razzak`: Admin type user. Represents a teacher (institutional user).
1. `ananta`: Non-admin type users. Represents a student.

### User Attributres

Each user has a few *user attributues* defined on them. These are `eiin`,
`crvsId` and `teacherId`.

The `ieims` client scope maps the user attributes to claims in the
JWT tokens.
