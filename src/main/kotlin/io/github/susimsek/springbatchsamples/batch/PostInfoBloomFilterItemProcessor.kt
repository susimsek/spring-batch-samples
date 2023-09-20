package io.github.susimsek.springbatchsamples.batch

import io.github.susimsek.springbatchsamples.domain.Post
import io.github.susimsek.springbatchsamples.mapper.PostMapper
import io.github.susimsek.springbatchsamples.model.PostSummary
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor

class PostInfoBloomFilterItemProcessor(
    private val postMapper: PostMapper
) : ItemProcessor<Post, PostSummary> {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun process(item: Post): PostSummary? {
        log.info("processing the item: {}", item.toString())
        return postMapper.toPostSummary(item)
    }
}
