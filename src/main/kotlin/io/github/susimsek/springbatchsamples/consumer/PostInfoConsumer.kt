package io.github.susimsek.springbatchsamples.consumer

import io.github.susimsek.springbatchsamples.model.PostPayload
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PostInfoConsumer {

    private val log = LoggerFactory.getLogger(javaClass)


    @KafkaListener(
        topics = ["\${spring.kafka.template.default-topic}"],
        groupId = "\${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun consume(payload: PostPayload) {
        log.info("Consumed post event -> {}", payload)
    }
}