FROM quay.io/keycloak/keycloak:26.4.2

WORKDIR /opt/keycloak

# Copying SPI JAR into the providers folder
# Make sure to build JAR before building this Docker image
ARG JAR_VERSION
COPY target/keycloak-kafka-event-listener-${JAR_VERSION}.jar /opt/keycloak/providers/

# Demo purposes only
ENV KC_BOOTSTRAP_ADMIN_USERNAME=admin
ENV KC_BOOTSTRAP_ADMIN_PASSWORD=admin

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--verbose"]