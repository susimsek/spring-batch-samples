package io.github.susimsek.springbatchsamples.batch

import io.github.susimsek.springbatchsamples.domain.Post
import io.github.susimsek.springbatchsamples.mapper.PostMapper
import io.github.susimsek.springbatchsamples.model.PostPayload
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor

class PostInfoItemProcessor(private val postMapper: PostMapper) : ItemProcessor<Post, PostPayload> {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun process(item: Post): PostPayload? {
        log.info("processing the item: {}", item.toString())
        return postMapper.toDto(item)
    }
}
