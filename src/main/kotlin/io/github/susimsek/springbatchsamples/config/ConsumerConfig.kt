package io.github.susimsek.springbatchsamples.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.susimsek.springbatchsamples.model.PostPayload
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration(proxyBeanMethods = false)
@EnableKafka
class ConsumerConfig {

    @Bean
    fun kafkaProducerFactoryCustomizer(
        objectMapper: ObjectMapper) = DefaultKafkaProducerFactoryCustomizer {
        producerFactory ->
        producerFactory.valueSerializer = JsonSerializer(objectMapper)
    }

    @Bean
    fun consumerFactory(
        kafkaProperties: KafkaProperties,
        objectMapper: ObjectMapper
    ): ConsumerFactory<String, PostPayload> {
        val props = kafkaProperties.buildConsumerProperties()
        props[ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG] = 9000
        props[ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG] = 120000
        return DefaultKafkaConsumerFactory(
            props, StringDeserializer(),
            JsonDeserializer(PostPayload::class.java, objectMapper)
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, PostPayload>
    ): ConcurrentKafkaListenerContainerFactory<String, PostPayload> {
        val factory: ConcurrentKafkaListenerContainerFactory<String, PostPayload> =
            ConcurrentKafkaListenerContainerFactory<String, PostPayload>()
        factory.consumerFactory = consumerFactory
        return factory
    }
}
