# -----------------------------------------------------------------------------------
# This Dockerfile is intentionally simplified and designed only for local development
# and testing of the Kafka Event Listener SPI. It runs Keycloak in 'start-dev' mode,
# which uses the embedded H2 database and enables development-friendly defaults.
#
# This setup is not suitable for production environments. For production configuration,
# please refer to the official Keycloak documentations.
#
#   - Container best practices    (https://www.keycloak.org/server/containers)
#   - Server configuration guide: (https://www.keycloak.org/server/configuration)
# -----------------------------------------------------------------------------------
FROM quay.io/keycloak/keycloak:latest AS builder

ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

WORKDIR /opt/keycloak

# Copying SPI JAR into the providers folder
# Make sure to build JAR before building this Docker image
ARG JAR_VERSION
COPY target/keycloak-kafka-event-listener-${JAR_VERSION}.jar /opt/keycloak/providers/

RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:latest
COPY --from=builder /opt/keycloak/ /opt/keycloak/

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--verbose"]