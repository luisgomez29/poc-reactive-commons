package co.com.bancolombia.config.rabbitmq;

public record RabbitMQConnectionProperties(
        String virtualhost,
        String host,
        String password,
        String username,
        boolean ssl,
        Integer port) {
}
