package io.github.susimsek.springbatchsamples.cache

import org.hibernate.boot.registry.StandardServiceRegistry
import org.redisson.api.RedissonClient
import org.redisson.hibernate.RedissonRegionFactory

class SpringRedissonRegionFactory(
    private val redissonClient: RedissonClient) : RedissonRegionFactory() {

    override fun createRedissonClient(
        registry: StandardServiceRegistry,
        properties: MutableMap<Any?, Any?>
    ): RedissonClient {
        return redissonClient;
    }
}