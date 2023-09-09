package io.github.susimsek.springbatchsamples.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.susimsek.springbatchsamples.redis.SpringBootRedissonRegionFactory
import org.hibernate.cfg.AvailableSettings
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.Clock
import java.time.OffsetDateTime
import java.util.*


@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories("io.github.susimsek.springbatchsamples.repository")
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableTransactionManagement
class DatabaseConfig {

    @Bean
    fun clock(): Clock {
        return Clock.systemDefaultZone()
    }

    @Bean
    fun dateTimeProvider(clock: Clock): DateTimeProvider {
        return DateTimeProvider { Optional.of(OffsetDateTime.now(clock)) }
    }


    @Bean
    fun redissonClient(
        @Value("classpath:/redisson.yml") configFile: Resource,
        objectMapper: ObjectMapper): RedissonClient {
        val config = Config.fromYAML(configFile.inputStream)
        return Redisson.create(config)
    }


    @Bean
    fun hibernatePropertiesCustomizer(redissonClient: RedissonClient): HibernatePropertiesCustomizer {
        return HibernatePropertiesCustomizer { hibernateProperties: MutableMap<String, Any> ->
            hibernateProperties[AvailableSettings.CACHE_REGION_FACTORY] =
                SpringBootRedissonRegionFactory(redissonClient)
        }
    }
}
