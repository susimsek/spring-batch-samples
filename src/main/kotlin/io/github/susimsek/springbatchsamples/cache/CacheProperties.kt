package io.github.susimsek.springbatchsamples.cache

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConfigurationProperties("cache")
data class CacheProperties(
    @NestedConfigurationProperty
    var redis: Redis
)

data class Redis(
    var server: MutableList<String> = CacheDefaults.Redis.server,
    var expiration: Int = CacheDefaults.Redis.expiration,
    var isCluster: Boolean = CacheDefaults.Redis.cluster,
    var connectionPoolSize: Int = CacheDefaults.Redis.connectionPoolSize,
    var connectionMinimumIdleSize: Int = CacheDefaults.Redis.connectionMinimumIdleSize,
    var subscriptionConnectionPoolSize: Int = CacheDefaults.Redis.subscriptionConnectionPoolSize,
    var subscriptionConnectionMinimumIdleSize: Int = CacheDefaults.Redis.subscriptionConnectionMinimumIdleSize,
    var username: String = CacheDefaults.Redis.username,
    var password: String?,
    var dnsMonitoringInterval: Long = CacheDefaults.Redis.dnsMonitoringInterval
)