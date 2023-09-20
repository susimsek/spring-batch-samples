package io.github.susimsek.springbatchsamples.batch

import org.redisson.api.RBloomFilter
import org.slf4j.LoggerFactory
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.InitializingBean
import org.springframework.util.Assert
import org.springframework.util.CollectionUtils

class RedisBloomFilterItemWriter<T> : ItemWriter<T>, InitializingBean {
    private lateinit var bloomFilter: RBloomFilter<T>

    private val log = LoggerFactory.getLogger(javaClass)

    override fun write(chunk: Chunk<out T>) {
        if (!CollectionUtils.isEmpty(chunk.items)) {
            this.doWrite(chunk)
        }
    }

    private fun doWrite(items: Chunk<out T>) {
        if (log.isDebugEnabled) {
            log.debug("Writing to the repository with " + items.size() + " items.");
        }
        val var3 = items.iterator()

        while (var3.hasNext()) {
            val `object`: T = var3.next()
            bloomFilter.add(`object`)
        }
    }

    fun setBloomFilter(bloomFilter: RBloomFilter<T>) {
        this.bloomFilter = bloomFilter;
    }

    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        Assert.state(this.bloomFilter != null, "A RBloomFilter implementation is required")
    }
}