package co.com.bancolombia.config.kafka;

import lombok.extern.log4j.Log4j2;
import org.reactivecommons.async.kafka.config.KafkaProperties;
import org.reactivecommons.async.kafka.config.props.AsyncKafkaProps;
import org.reactivecommons.async.kafka.config.props.AsyncKafkaPropsDomainProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
@Log4j2
public class KafkaConfigHelper {

    @Bean
    @Primary
    public AsyncKafkaPropsDomainProperties customKafkaDomainProperties() {
        KafkaProperties propertiesApp = new KafkaProperties();
        propertiesApp.setBootstrapServers(List.of("localhost:9092"));

        return AsyncKafkaPropsDomainProperties.builder()
                .withDomain("app", AsyncKafkaProps.builder()
                        .connectionProperties(propertiesApp)
                        .build())
                .build();
    }

}
