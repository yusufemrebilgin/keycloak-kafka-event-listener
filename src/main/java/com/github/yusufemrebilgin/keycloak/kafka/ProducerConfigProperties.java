package com.github.yusufemrebilgin.keycloak.kafka;

/**
 * Entries correspond directly to the official Kafka producer options. <p>
 * See: <a href="https://kafka.apache.org/documentation/#producerconfigs">Apache Kafka Producer Configurations</a>
 */
enum ProducerConfigProperties {

    KEY_SERIALIZER("key.serializer"),
    VALUE_SERIALIZER("value.serializer"),
    BOOTSTRAP_SERVERS("bootstrap.servers"),
    BUFFER_MEMORY("buffer.memory"),
    COMPRESSION_TYPE("compression.type"),
    RETRIES("retries"),
    SSL_KEY_PASSWORD("ssl.key.password"),
    SSL_KEYSTORE_CERTIFICATE_CHAIN("ssl.keystore.certificate.chain"),
    SSL_KEYSTORE_KEY("ssl.keystore.key"),
    SSL_KEYSTORE_LOCATION("ssl.keystore.location"),
    SSL_KEYSTORE_PASSWORD("ssl.keystore.password"),
    SSL_TRUSTSTORE_CERTIFICATES("ssl.truststore.certificates"),
    SSL_TRUSTSTORE_LOCATION("ssl.truststore.location"),
    SSL_TRUSTSTORE_PASSWORD("ssl.truststore.password"),
    BATCH_SIZE("batch.size"),
    CLIENT_DNS_LOOKUP("client.dns.lookup"),
    CLIENT_ID("client.id"),
    COMPRESSION_GZIP_LEVEL("compression.gzip.level"),
    COMPRESSION_LZ4_LEVEL("compression.lz4.level"),
    COMPRESSION_ZSTD_LEVEL("compression.zstd.level"),
    CONNECTIONS_MAX_IDLE_MS("connections.max.idle.ms"),
    DELIVERY_TIMEOUT_MS("delivery.timeout.ms"),
    LINGER_MS("linger.ms"),
    MAX_BLOCK_MS("max.block.ms"),
    MAX_REQUEST_SIZE("max.request.size"),
    PARTITIONER_CLASS("partitioner.class"),
    PARTITIONER_IGNORE_KEYS("partitioner.ignore.keys"),
    RECEIVE_BUFFER_BYTES("receive.buffer.bytes"),
    REQUEST_TIMEOUT_MS("request.timeout.ms"),
    SASL_CLIENT_CALLBACK_HANDLER_CLASS("sasl.client.callback.handler.class"),
    SASL_KERBEROS_SERVICE_NAME("sasl.kerberos.service.name"),
    SASL_LOGIN_CALLBACK_HANDLER_CLASS("sasl.login.callback.handler.class"),
    SASL_LOGIN_CLASS("sasl.login.class"),
    SASL_MECHANISM("sasl.mechanism"),
    SASL_OAUTHBEARER_ASSERTION_ALGORITHM("sasl.oauthbearer.assertion.algorithm"),
    SASL_OAUTHBEARER_ASSERTION_CLAIM_AUD("sasl.oauthbearer.assertion.claim.aud"),
    SASL_OAUTHBEARER_ASSERTION_CLAIM_ISS("sasl.oauthbearer.assertion.claim.iss"),
    SASL_OAUTHBEARER_ASSERTION_CLAIM_JTI_INCLUDE("sasl.oauthbearer.assertion.claim.jti.include"),
    SASL_OAUTHBEARER_ASSERTION_CLAIM_SUB("sasl.oauthbearer.assertion.claim.sub"),
    SASL_OAUTHBEARER_ASSERTION_FILE("sasl.oauthbearer.assertion.file"),
    SASL_OAUTHBEARER_ASSERTION_PRIVATE_KEY_FILE("sasl.oauthbearer.assertion.private.key.file"),
    SASL_OAUTHBEARER_ASSERTION_PRIVATE_KEY_PASSPHRASE("sasl.oauthbearer.assertion.private.key.passphrase"),
    SASL_OAUTHBEARER_ASSERTION_TEMPLATE_FILE("sasl.oauthbearer.assertion.template.file"),
    SASL_OAUTHBEARER_CLIENT_CREDENTIALS_CLIENT_ID("sasl.oauthbearer.client.credentials.client.id"),
    SASL_OAUTHBEARER_CLIENT_CREDENTIALS_CLIENT_SECRET("sasl.oauthbearer.client.credentials.client.secret"),
    SASL_OAUTHBEARER_JWKS_ENDPOINT_URL("sasl.oauthbearer.jwks.endpoint.url"),
    SASL_OAUTHBEARER_JWT_RETRIEVER_CLASS("sasl.oauthbearer.jwt.retriever.class"),
    SASL_OAUTHBEARER_JWT_VALIDATOR_CLASS("sasl.oauthbearer.jwt.validator.class"),
    SASL_OAUTHBEARER_SCOPE("sasl.oauthbearer.scope"),
    SASL_OAUTHBEARER_TOKEN_ENDPOINT_URL("sasl.oauthbearer.token.endpoint.url"),
    SECURITY_PROTOCOL("security.protocol"),
    SEND_BUFFER_BYTES("send.buffer.bytes"),
    SOCKET_CONNECTION_SETUP_TIMEOUT_MAX_MS("socket.connection.setup.timeout.max.ms"),
    SOCKET_CONNECTION_SETUP_TIMEOUT_MS("socket.connection.setup.timeout.ms"),
    SSL_ENABLED_PROTOCOLS("ssl.enabled.protocols"),
    SSL_KEYSTORE_TYPE("ssl.keystore.type"),
    SSL_PROTOCOL("ssl.protocol"),
    SSL_PROVIDER("ssl.provider"),
    SSL_TRUSTSTORE_TYPE("ssl.truststore.type"),
    ACKS("acks"),
    ENABLE_IDEMPOTENCE("enable.idempotence"),
    ENABLE_METRICS_PUSH("enable.metrics.push"),
    INTERCEPTOR_CLASSES("interceptor.classes"),
    MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION("max.in.flight.requests.per.connection"),
    METADATA_MAX_AGE_MS("metadata.max.age.ms"),
    METADATA_MAX_IDLE_MS("metadata.max.idle.ms"),
    METADATA_RECOVERY_REBOOTSTRAP_TRIGGER_MS("metadata.recovery.rebootstrap.trigger.ms"),
    METADATA_RECOVERY_STRATEGY("metadata.recovery.strategy"),
    METRIC_REPORTERS("metric.reporters"),
    METRICS_NUM_SAMPLES("metrics.num.samples"),
    METRICS_RECORDING_LEVEL("metrics.recording.level"),
    METRICS_SAMPLE_WINDOW_MS("metrics.sample.window.ms"),
    PARTITIONER_ADAPTIVE_PARTITIONING_ENABLE("partitioner.adaptive.partitioning.enable"),
    PARTITIONER_AVAILABILITY_TIMEOUT_MS("partitioner.availability.timeout.ms"),
    RECONNECT_BACKOFF_MAX_MS("reconnect.backoff.max.ms"),
    RECONNECT_BACKOFF_MS("reconnect.backoff.ms"),
    RETRY_BACKOFF_MAX_MS("retry.backoff.max.ms"),
    RETRY_BACKOFF_MS("retry.backoff.ms"),
    SASL_KERBEROS_KINIT_CMD("sasl.kerberos.kinit.cmd"),
    SASL_KERBEROS_MIN_TIME_BEFORE_RELOGIN("sasl.kerberos.min.time.before.relogin"),
    SASL_KERBEROS_TICKET_RENEW_JITTER("sasl.kerberos.ticket.renew.jitter"),
    SASL_KERBEROS_TICKET_RENEW_WINDOW_FACTOR("sasl.kerberos.ticket.renew.window.factor"),
    SASL_LOGIN_CONNECT_TIMEOUT_MS("sasl.login.connect.timeout.ms"),
    SASL_LOGIN_READ_TIMEOUT_MS("sasl.login.read.timeout.ms"),
    SASL_LOGIN_REFRESH_BUFFER_SECONDS("sasl.login.refresh.buffer.seconds"),
    SASL_LOGIN_REFRESH_MIN_PERIOD_SECONDS("sasl.login.refresh.min.period.seconds"),
    SASL_LOGIN_REFRESH_WINDOW_FACTOR("sasl.login.refresh.window.factor"),
    SASL_LOGIN_REFRESH_WINDOW_JITTER("sasl.login.refresh.window.jitter"),
    SASL_LOGIN_RETRY_BACKOFF_MAX_MS("sasl.login.retry.backoff.max.ms"),
    SASL_LOGIN_RETRY_BACKOFF_MS("sasl.login.retry.backoff.ms"),
    SASL_OAUTHBEARER_ASSERTION_CLAIM_EXP_SECONDS("sasl.oauthbearer.assertion.claim.exp.seconds"),
    SASL_OAUTHBEARER_ASSERTION_CLAIM_NBF_SECONDS("sasl.oauthbearer.assertion.claim.nbf.seconds"),
    SASL_OAUTHBEARER_CLOCK_SKEW_SECONDS("sasl.oauthbearer.clock.skew.seconds"),
    SASL_OAUTHBEARER_EXPECTED_AUDIENCE("sasl.oauthbearer.expected.audience"),
    SASL_OAUTHBEARER_EXPECTED_ISSUER("sasl.oauthbearer.expected.issuer"),
    SASL_OAUTHBEARER_HEADER_URLENCODE("sasl.oauthbearer.header.urlencode"),
    SASL_OAUTHBEARER_JWKS_ENDPOINT_REFRESH_MS("sasl.oauthbearer.jwks.endpoint.refresh.ms"),
    SASL_OAUTHBEARER_JWKS_ENDPOINT_RETRY_BACKOFF_MAX_MS("sasl.oauthbearer.jwks.endpoint.retry.backoff.max.ms"),
    SASL_OAUTHBEARER_JWKS_ENDPOINT_RETRY_BACKOFF_MS("sasl.oauthbearer.jwks.endpoint.retry.backoff.ms"),
    SASL_OAUTHBEARER_SCOPE_CLAIM_NAME("sasl.oauthbearer.scope.claim.name"),
    SASL_OAUTHBEARER_SUB_CLAIM_NAME("sasl.oauthbearer.sub.claim.name"),
    SECURITY_PROVIDERS("security.providers"),
    SSL_CIPHER_SUITES("ssl.cipher.suites"),
    SSL_ENDPOINT_IDENTIFICATION_ALGORITHM("ssl.endpoint.identification.algorithm"),
    SSL_ENGINE_FACTORY_CLASS("ssl.engine.factory.class"),
    SSL_KEYMANAGER_ALGORITHM("ssl.keymanager.algorithm"),
    SSL_SECURE_RANDOM_IMPLEMENTATION("ssl.secure.random.implementation"),
    SSL_TRUSTMANAGER_ALGORITHM("ssl.trustmanager.algorithm"),
    TRANSACTION_TIMEOUT_MS("transaction.timeout.ms"),
    TRANSACTION_TWO_PHASE_COMMIT_ENABLE("transaction.two.phase.commit.enable"),
    TRANSACTIONAL_ID("transactional.id"),

    /**
     * Custom property used by the Keycloak Kafka Event Listener. Not part of the official Kafka configuration.
     * This allows adding a prefix to all topic names used by the listener.
     * <p>
     * For example: <code>user-events</code> would make the listener publish
     * to topics like <code>user-events.login</code> or <code>user-events.register</code>.
     */
    TOPIC_PREFIX("topic.prefix");

    private final String key;

    ProducerConfigProperties(String key) {
        this.key = key;
    }

    String getKey() {
        return key;
    }

}
