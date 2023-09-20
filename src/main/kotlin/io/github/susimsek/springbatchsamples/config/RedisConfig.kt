package io.github.susimsek.springbatchsamples.config

import io.github.susimsek.springbatchsamples.cache.CacheProperties
import io.github.susimsek.springbatchsamples.model.PostSummary
import org.redisson.Redisson
import org.redisson.api.RBloomFilter
import org.redisson.api.RedissonClient
import org.redisson.codec.SnappyCodecV2
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils

@Configuration(proxyBeanMethods = false)
class RedisConfig {

    @Bean(destroyMethod="shutdown")
    fun redissonClient(
        cacheProperties: CacheProperties
    ): RedissonClient {
        var config = Config()
        if (cacheProperties.redis.isCluster) {
            val clusterServers = config
                .useClusterServers()
                .setMasterConnectionPoolSize(cacheProperties.redis.connectionPoolSize)
                .setMasterConnectionMinimumIdleSize(
                    cacheProperties.redis.connectionMinimumIdleSize
                )
                .setSubscriptionConnectionPoolSize(
                    cacheProperties.redis.subscriptionConnectionPoolSize
                )
                .addNodeAddress(*cacheProperties.redis.server.toTypedArray())
                .setDnsMonitoringInterval(cacheProperties.redis.dnsMonitoringInterval)
                .setUsername(cacheProperties.redis.username)
            if (StringUtils.hasText(cacheProperties.redis.password)) {
                clusterServers.password = cacheProperties.redis.password
            }
            config.codec = SnappyCodecV2()
            return Redisson.create(config)
        } else {
            val singleServer = config
                .useSingleServer()
                .setConnectionPoolSize(cacheProperties.redis.connectionPoolSize)
                .setConnectionMinimumIdleSize(cacheProperties.redis.connectionMinimumIdleSize)
                .setSubscriptionConnectionPoolSize(
                    cacheProperties.redis.subscriptionConnectionPoolSize
                )
                .setAddress(cacheProperties.redis.server[0])
                .setDnsMonitoringInterval(cacheProperties.redis.dnsMonitoringInterval)
                .setUsername(cacheProperties.redis.username)
            if (StringUtils.hasText(cacheProperties.redis.password)) {
                singleServer.password = cacheProperties.redis.password
            }
            config.codec = SnappyCodecV2()
        }
        return Redisson.create(config)
    }

    @Bean
    fun titleBloomFilter(redissonClient: RedissonClient): RBloomFilter<String> {
        val bloomFilter = redissonClient.getBloomFilter<String>("titles")
        bloomFilter.tryInit(2, 0.03)
        return bloomFilter
    }

    @Bean
    fun postSummaryBloomFilter(redissonClient: RedissonClient): RBloomFilter<PostSummary> {
        val bloomFilter = redissonClient.getBloomFilter<PostSummary>("postSummaries")
        bloomFilter.tryInit(2, 0.03)
        return bloomFilter
    }
}