package com.learnings.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;
import java.util.Collections;

@Data
@Configuration
public class ReactiveKafkaConsumerConfig<K, V> {

    @Value("${kafka.topic.user}")
    private String userTopic;

    @Bean
    public ReceiverOptions<K, V> kafkaReceiverOptions(KafkaProperties kafkaProperties) {
        ReceiverOptions<K, V> receiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        return receiverOptions.subscription(Collections.singletonList(userTopic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<K, V> reactiveKafkaConsumerTemplate(ReceiverOptions<K, V> receiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(receiverOptions);
    }
}
