package io.github.susimsek.springbatchsamples.batch

import io.github.susimsek.springbatchsamples.model.PostPayload
import org.springframework.batch.item.kafka.KafkaItemWriter
import org.springframework.kafka.core.KafkaTemplate

class PostInfoItemWriter(
    kafkaTemplate: KafkaTemplate<String, PostPayload>
) : KafkaItemWriter<String, PostPayload>() {

    init {
        setKafkaTemplate(kafkaTemplate)
        setItemKeyMapper{ it.id }
        setDelete(false)
        afterPropertiesSet()
    }
}
