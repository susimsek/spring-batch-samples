package io.github.susimsek.springbatchsamples

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

@SpringBootApplication
class SpringBatchSamplesApplication

fun main(args: Array<String>) {
    exitProcess(
        SpringApplication.exit(
            runApplication<SpringBatchSamplesApplication>(*args)
        )
    )
}
