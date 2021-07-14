#
# Export the full realm with users to a file
#
REALM_NAME=development

docker exec -it keycloak /opt/jboss/keycloak/bin/standalone.sh \
	-Djboss.socket.binding.port-offset=100 \
	-Dkeycloak.migration.action=export \
	-Dkeycloak.migration.provider=dir \
	-Dkeycloak.migration.dir=/realm-data \
	-Dkeycloak.migration.realmName=$REALM_NAME
