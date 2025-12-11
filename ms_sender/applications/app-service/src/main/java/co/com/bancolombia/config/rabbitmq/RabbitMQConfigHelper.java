package co.com.bancolombia.config.rabbitmq;

import org.reactivecommons.async.rabbit.config.RabbitProperties;
import org.reactivecommons.async.rabbit.config.props.AsyncProps;
import org.reactivecommons.async.rabbit.config.props.AsyncRabbitPropsDomainProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class RabbitMQConfigHelper {

    private final RabbitMQConnectionProperties propertiesPush;
    private final RabbitMQConnectionProperties propertiesAlert;
    private final Boolean withDLQRetry;
    private final Integer maxRetries;
    private final Integer retryDelay;

    public RabbitMQConfigHelper(@Qualifier("rabbit") RabbitMQConnectionProperties propertiesPush,
                                @Qualifier("rabbitDual") RabbitMQConnectionProperties propertiesAlert,
                                @Value("${adapters.rabbitmq.withDLQRetry}") Boolean withDLQRetry,
                                @Value("${adapters.rabbitmq.maxRetries}") Integer maxRetries,
                                @Value("${adapters.rabbitmq.retryDelay}") Integer retryDelay) {
        this.propertiesPush = propertiesPush;
        this.propertiesAlert = propertiesAlert;
        this.withDLQRetry = withDLQRetry;
        this.maxRetries = maxRetries;
        this.retryDelay = retryDelay;
    }

    @Bean
    @Primary
    public AsyncRabbitPropsDomainProperties customDomainProperties() {
        var propertiesReceive = new RabbitProperties();
        propertiesReceive.setHost(propertiesAlert.host());
        propertiesReceive.setPort(propertiesAlert.port());
        propertiesReceive.setVirtualHost(propertiesAlert.virtualhost());
        propertiesReceive.setUsername(propertiesAlert.username());
        propertiesReceive.setPassword(propertiesAlert.password());
        propertiesReceive.getSsl().setEnabled(propertiesAlert.ssl());

        var propertiesSend = new RabbitProperties();
        propertiesSend.setHost(propertiesPush.host());
        propertiesSend.setPort(propertiesPush.port());
        propertiesSend.setVirtualHost(propertiesPush.virtualhost());
        propertiesSend.setUsername(propertiesPush.username());
        propertiesSend.setPassword(propertiesPush.password());
        propertiesSend.getSsl().setEnabled(propertiesPush.ssl());

        return AsyncRabbitPropsDomainProperties.builder()
                .withDomain("app", AsyncProps.builder()
                        .connectionProperties(propertiesSend)
                        .withDLQRetry(withDLQRetry)
                        .maxRetries(maxRetries)
                        .retryDelay(retryDelay)
                        .listenReplies(Boolean.TRUE)
                        .build())
                .withDomain("push", AsyncProps.builder()
                        .connectionProperties(propertiesReceive)
                        .withDLQRetry(Boolean.TRUE)
                        .maxRetries(maxRetries)
                        .retryDelay(retryDelay)
                        .listenReplies(Boolean.FALSE)
//                        .queueType("quorum")
                        .build())
                .build();
    }
}
