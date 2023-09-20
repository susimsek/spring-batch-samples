package io.github.susimsek.springbatchsamples.service

import org.redisson.api.RBloomFilter
import org.springframework.stereotype.Service


@Service
class PostBloomFilterService(
    private val titleBloomFilter: RBloomFilter<String>
) {

    fun checkIfTitleAvailability(title: String): Boolean {
        return titleBloomFilter.contains(title)
    }
}