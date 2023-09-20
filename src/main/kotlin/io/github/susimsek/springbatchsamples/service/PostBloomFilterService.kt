package io.github.susimsek.springbatchsamples.service

import io.github.susimsek.springbatchsamples.model.PostSummary
import org.redisson.api.RBloomFilter
import org.springframework.stereotype.Service

@Service
class PostBloomFilterService(
    private val titleBloomFilter: RBloomFilter<String>,
    private val postSummaryBloomFilter: RBloomFilter<PostSummary>
) {

    fun checkIfTitleAvailability(title: String): Boolean {
        return titleBloomFilter.contains(title)
    }

    fun checkIfPostSummaryAvailability(summary: PostSummary): Boolean {
        return postSummaryBloomFilter.contains(summary)
    }
}
