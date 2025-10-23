package com.thrivent.tax.lettermanager.config;

import com.thrivent.tax.lettermanager.properties.KafkaProperties;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Data
@Slf4j
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;

    /*
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        String bootstrapServers = kafkaProperties.getBootstrapServers();
        if (bootstrapServers == null || bootstrapServers.isBlank()) {
            log.error("Kafka bootstrapServers is blank");
            throw new IllegalArgumentException("spring.kafka.bootstrap-servers is missing or empty");
        }
        String schemaRegistryUrl = kafkaProperties.getProperties().get("schema.registry.url");
        // String credentialsSource = kafkaProperties.getProperties().get("basic.auth.credentials.source");
        // String userInfo = kafkaProperties.getProperties().get("basic.auth.user.info");
        // if (schemaRegistryUrl == null || credentialsSource == null || userInfo == null) {
        //    throw new IllegalArgumentException("Schema Registry authentication properties are missing");
        // }
        // config.put("basic.auth.credentials.source", credentialsSource);
        // config.put("basic.auth.user.info", userInfo);
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        config.put("schema.registry.url", schemaRegistryUrl);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
*/
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        // Required values
        String bootstrapServers = kafkaProperties.getBootstrapServers();
        if (bootstrapServers == null || bootstrapServers.isEmpty()) {
            throw new IllegalArgumentException("spring.kafka.bootstrap-servers must be set");
        }
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        if (kafkaProperties.getConsumer() != null && kafkaProperties.getConsumer().getGroupId() != null) {
            config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
        } else {
            throw new IllegalArgumentException("spring.kafka.consumer.group-id must be set");
        }
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        String schemaRegistryUrl = kafkaProperties.getProperties().get("schema.registry.url");
        if (schemaRegistryUrl != null) {
            config.put("schema.registry.url", schemaRegistryUrl);
        }
        config.put("specific.avro.reader", true);
        String credentialsSource = kafkaProperties.getProperties().get("basic.auth.credentials.source");
        if (credentialsSource != null) {
            config.put("basic.auth.credentials.source", credentialsSource);
        }
        String userInfo = kafkaProperties.getProperties().get("basic.auth.user.info");
        if (userInfo != null) {
            config.put("basic.auth.user.info", userInfo);
        }
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
