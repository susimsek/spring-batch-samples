package io.github.susimsek.springbatchsamples.config

import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportRuntimeHints
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration(proxyBeanMethods = false)
@EnableScheduling
@ImportRuntimeHints(BatchConfig.BatchRuntimeHints::class)
class BatchConfig {

    internal class BatchRuntimeHints : RuntimeHintsRegistrar {
        private val values: Array<MemberCategory> = MemberCategory.values()

        override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
        }
    }
}
