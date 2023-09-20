package io.github.susimsek.springbatchsamples.batch

import io.github.susimsek.springbatchsamples.domain.Post
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor

class PostInfoBloomFilterItemProcessor() : ItemProcessor<Post, String> {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun process(item: Post): String? {
        log.info("processing the item: {}", item.toString())
        return item.title
    }
}
