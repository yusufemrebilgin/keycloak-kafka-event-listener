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
