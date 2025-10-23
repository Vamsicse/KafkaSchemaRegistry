package com.thrivent.tax.lettermanager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
@Data
public class KafkaProperties {

    private String bootstrapServers;
    private String clientId;
    private String schemaRegistryUrl;
    private Producer producer;
    private Consumer consumer;
    private Map<String, String> properties;
    private String topic;

    @Data
    public static class Producer {
        private String keySerializer;
        private String valueSerializer;
        private Map<String, String> properties;
    }

    @Data
    public static class Consumer {
        private String groupId;
        private String keyDeserializer;
        private String valueDeserializer;
        private Map<String, String> properties;
    }
}
