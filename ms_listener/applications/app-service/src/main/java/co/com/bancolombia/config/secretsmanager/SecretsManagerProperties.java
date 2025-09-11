package co.com.bancolombia.config.secretsmanager;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapters.secrets-manager")
public record SecretsManagerProperties(
        String endpoint,
        int cacheSize,
        int cacheTime,
        String rabbit,
        String rabbitDual) {
}
