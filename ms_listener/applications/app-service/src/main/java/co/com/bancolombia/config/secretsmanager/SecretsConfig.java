package co.com.bancolombia.config.secretsmanager;

import co.com.bancolombia.config.rabbitmq.RabbitMQConnectionProperties;
import co.com.bancolombia.secretsmanager.config.AWSSecretsManagerConfig;
import co.com.bancolombia.secretsmanager.connector.AWSSecretManagerConnectorAsync;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.regions.Region;


@Log4j2
@Profile("local")
@Configuration
@RequiredArgsConstructor
public class SecretsConfig {
    private static final Region REGION_SECRET = Region.US_EAST_1;

    private final SecretsManagerProperties properties;

//    @Bean
//    @Profile({"dev", "qa", "pdn"})
//    public AWSSecretManagerConnectorAsync connectionAws() {
//        return new AWSSecretManagerConnectorAsync(secretsManagerConfigBuilder().build());
//    }

    @Bean
    public AWSSecretManagerConnectorAsync connectionLocal() {
        return new AWSSecretManagerConnectorAsync(secretsManagerConfigBuilder()
                .endpoint(properties.endpoint())
                .build());
    }

    private AWSSecretsManagerConfig.AWSSecretsManagerConfigBuilder secretsManagerConfigBuilder() {
        return AWSSecretsManagerConfig
                .builder()
                .region(REGION_SECRET)
                .cacheSize(properties.cacheSize())
                .cacheSeconds(properties.cacheTime());
    }

    private <T> Mono<T> getSecret(String secretName, Class<T> cls, AWSSecretManagerConnectorAsync connector) {
        return connector.getSecret(secretName, cls)
                .doOnSuccess(e -> log.info("Secret was obtained successfully"))
                .doOnError(e -> log.error("Error getting secret: {}", e.getMessage()))
                .onErrorMap(e -> new RuntimeException("Error getting secret", e));
    }

    @Bean("rabbit")
    public RabbitMQConnectionProperties getSecretRabbit(AWSSecretManagerConnectorAsync connector) {
        return getSecret(properties.rabbit(), RabbitMQConnectionProperties.class, connector).block();
    }

    @Bean("rabbitDual")
    public RabbitMQConnectionProperties getSecretRabbitDual(AWSSecretManagerConnectorAsync connector) {
        return getSecret(properties.rabbitDual(), RabbitMQConnectionProperties.class, connector).block();
    }
}
