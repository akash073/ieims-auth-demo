#
# Run the official Keycloak docker image
#
KEYCLOAK_VERSION=14.0.0
REALM_NAME=development

docker run -p 8000:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin \
	--mount type=bind,src="$(pwd)"/realm-data,dst=/realm-data \
	-e JAVA_OPTS_APPEND="-Dkeycloak.migration.action=import -Dkeycloak.migration.provider=dir -Dkeycloak.migration.dir=/realm-data -Dkeycloak.migration.realmName=$REALM_NAME" \
	--name keycloak-temp \
	--network host \
	quay.io/keycloak/keycloak:${KEYCLOAK_VERSION}
