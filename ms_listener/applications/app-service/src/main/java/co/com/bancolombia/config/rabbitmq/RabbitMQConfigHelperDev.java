package co.com.bancolombia.config.rabbitmq;

import org.reactivecommons.async.rabbit.config.RabbitProperties;
import org.reactivecommons.async.rabbit.config.props.AsyncProps;
import org.reactivecommons.async.rabbit.config.props.AsyncRabbitPropsDomainProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class RabbitMQConfigHelperDev {

    private final RabbitMQConnectionPropertiesDev connectionProperties;
    private final Boolean withDLQRetry;
    private final Integer maxRetries;
    private final Integer retryDelay;

    public RabbitMQConfigHelperDev(RabbitMQConnectionPropertiesDev connectionProperties,
                                   @Value("${adapters.rabbitmq.withDLQRetry}") Boolean withDLQRetry,
                                   @Value("${adapters.rabbitmq.maxRetries}") Integer maxRetries,
                                   @Value("${adapters.rabbitmq.retryDelay}") Integer retryDelay) {
        this.connectionProperties = connectionProperties;
        this.withDLQRetry = withDLQRetry;
        this.maxRetries = maxRetries;
        this.retryDelay = retryDelay;
    }

    @Bean
    @Primary
    public AsyncRabbitPropsDomainProperties customDomainProperties() {
        var propertiesApp = new RabbitProperties();
        propertiesApp.setHost(connectionProperties.host());
        propertiesApp.setPort(connectionProperties.port());
        propertiesApp.setVirtualHost(connectionProperties.virtualhost());
        propertiesApp.setUsername(connectionProperties.username());
        propertiesApp.setPassword(connectionProperties.password());
        propertiesApp.getSsl().setEnabled(connectionProperties.ssl());

        return AsyncRabbitPropsDomainProperties.builder()
                .withDomain("app", AsyncProps.builder()
                        .connectionProperties(propertiesApp)
                        .withDLQRetry(withDLQRetry)
                        .maxRetries(maxRetries)
                        .retryDelay(retryDelay)
                        .listenReplies(Boolean.TRUE)
                        .build())
                .build();
    }

}
