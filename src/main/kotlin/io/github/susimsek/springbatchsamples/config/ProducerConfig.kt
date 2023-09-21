package io.github.susimsek.springbatchsamples.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.support.serializer.JsonSerializer

@EnableKafka
@Configuration(proxyBeanMethods = false)
class ProducerConfig {

    @Bean
    fun kafkaProducerFactoryCustomizer(
        objectMapper: ObjectMapper
    ) = DefaultKafkaProducerFactoryCustomizer {
            producerFactory ->
        producerFactory.valueSerializer = JsonSerializer(objectMapper)
    }
}
