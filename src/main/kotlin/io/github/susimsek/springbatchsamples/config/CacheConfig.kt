package io.github.susimsek.springbatchsamples.config

import io.github.susimsek.springbatchsamples.cache.CacheProperties
import io.github.susimsek.springbatchsamples.cache.SpringRedissonRegionFactory
import org.hibernate.cfg.AvailableSettings
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.codec.SnappyCodecV2
import org.redisson.config.Config
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheProperties::class)
class CacheConfig {
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
    @ConditionalOnProperty("spring.jpa.properties.hibernate.cache.use_second_level_cache")
    fun hibernatePropertiesCustomizer(redissonClient: RedissonClient): HibernatePropertiesCustomizer {
        return HibernatePropertiesCustomizer { hibernateProperties ->
            hibernateProperties[AvailableSettings.CACHE_REGION_FACTORY] = SpringRedissonRegionFactory(redissonClient)
        }
    }
}