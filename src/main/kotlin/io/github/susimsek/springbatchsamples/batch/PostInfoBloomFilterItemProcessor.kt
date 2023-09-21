package io.github.susimsek.springbatchsamples.batch

import io.github.susimsek.springbatchsamples.model.PostTitle
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor

class PostInfoBloomFilterItemProcessor : ItemProcessor<PostTitle, PostTitle> {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun process(item: PostTitle): PostTitle? {
        log.info("processing the item: {}", item.toString())
        return item
    }
}
