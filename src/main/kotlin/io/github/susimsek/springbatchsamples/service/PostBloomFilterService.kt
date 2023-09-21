package io.github.susimsek.springbatchsamples.service

import io.github.susimsek.springbatchsamples.aspect.MeasureTime
import io.github.susimsek.springbatchsamples.model.PostTitle
import org.redisson.api.RBloomFilter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PostBloomFilterService(
    private val titleBloomFilter: RBloomFilter<String>,
    private val postTitleBloomFilter: RBloomFilter<PostTitle>
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun checkIfTitleAvailability(title: String): Boolean {
        return titleBloomFilter.contains(title)
    }

    @MeasureTime
    fun checkIfPostTitleAvailability(postTitle: PostTitle): Boolean {
        log.info("checkIfPostTitleAvailability method {}", postTitle)
        return postTitleBloomFilter.contains(postTitle)
    }
}
