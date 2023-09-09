package io.github.susimsek.springbatchsamples.redis

import org.hibernate.boot.registry.StandardServiceRegistry
import org.redisson.api.RedissonClient
import org.redisson.hibernate.RedissonRegionFactory

class SpringBootRedissonRegionFactory(
    private val redissonClient: RedissonClient) : RedissonRegionFactory() {

    override fun createRedissonClient(
        registry: StandardServiceRegistry?,
        properties: MutableMap<Any?, Any?>?
    ): RedissonClient {
        return redissonClient;
    }

    override fun releaseFromUse() {}
}