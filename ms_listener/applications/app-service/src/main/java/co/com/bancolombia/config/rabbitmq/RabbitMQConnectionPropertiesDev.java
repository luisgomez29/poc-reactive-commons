package co.com.bancolombia.config.rabbitmq;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapters.rabbitmq.connection-properties")
public record RabbitMQConnectionPropertiesDev(
        String virtualhost,
        String host,
        String password,
        String username,
        boolean ssl,
        Integer port) {
}
