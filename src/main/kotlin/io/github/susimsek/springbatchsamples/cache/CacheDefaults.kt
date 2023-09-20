package io.github.susimsek.springbatchsamples.cache

class CacheDefaults {

    internal class Redis {
        companion object {
            val server = mutableListOf("redis://localhost:6379")
            const val expiration = 300 // 5 minutes
            const val cluster = false
            const val connectionPoolSize = 64 // default as in redisson
            const val connectionMinimumIdleSize = 24 // default as in redisson
            const val subscriptionConnectionPoolSize = 50 // default as in redisson
            const val subscriptionConnectionMinimumIdleSize = 1 // default as in redisson
            const val username = "default" // 5 minutes
            const val dnsMonitoringInterval = 5000L
        }
    }
}
