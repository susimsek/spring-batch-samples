package io.github.susimsek.springbatchsamples.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
}
