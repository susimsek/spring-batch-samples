package io.github.susimsek.springbatchsamples.config

import io.github.susimsek.springbatchsamples.cache.CacheProperties
import io.github.susimsek.springbatchsamples.cache.SpringRedissonRegionFactory
import org.hibernate.cfg.AvailableSettings
import org.redisson.api.RedissonClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheProperties::class)
class CacheConfig {

    @Bean
    @ConditionalOnProperty("spring.jpa.properties.hibernate.cache.use_second_level_cache")
    fun hibernatePropertiesCustomizer(redissonClient: RedissonClient): HibernatePropertiesCustomizer {
        return HibernatePropertiesCustomizer { hibernateProperties ->
            hibernateProperties[AvailableSettings.CACHE_REGION_FACTORY] = SpringRedissonRegionFactory(redissonClient)
        }
    }
}
