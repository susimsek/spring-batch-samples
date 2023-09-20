package io.github.susimsek.springbatchsamples.config

import io.github.susimsek.springbatchsamples.batch.PostInfoBloomFilterItemProcessor
import io.github.susimsek.springbatchsamples.batch.PostInfoItemProcessor
import io.github.susimsek.springbatchsamples.batch.RedisBloomFilterItemWriter
import io.github.susimsek.springbatchsamples.domain.Post
import io.github.susimsek.springbatchsamples.listener.JobCompletionNotificationListener
import io.github.susimsek.springbatchsamples.mapper.PostMapper
import io.github.susimsek.springbatchsamples.model.PostPayload
import org.redisson.api.RBloomFilter
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.integration.async.AsyncItemProcessor
import org.springframework.batch.integration.async.AsyncItemWriter
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.data.RepositoryItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.transaction.PlatformTransactionManager
import java.util.concurrent.Future


@Configuration(proxyBeanMethods = false)
class PostInfoBloomFilterJobConfig {

    @Bean
    fun postInfoBloomFilterJob(
        jobRepository: JobRepository,
        postInfoBloomFilterStep: Step,
        listener: JobCompletionNotificationListener
    ): Job {
        return JobBuilder("postInfoBloomFilterJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .listener(listener)
            .start(postInfoBloomFilterStep)
            .build()
    }

    @Bean
    @Suppress("LongParameterList")
    fun postInfoBloomFilterStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        taskExecutor: TaskExecutor,
        postInfoItemReader: RepositoryItemReader<Post>,
        asyncPostInfoBloomFilterItemProcessor: AsyncItemProcessor<Post, String>,
        asyncPostInfoBloomFilterItemWriter: AsyncItemWriter<String>
    ): Step {
        return StepBuilder("postInfoBloomFilterStep", jobRepository)
            .chunk<Post, Future<String>>(10, transactionManager)
            .reader(postInfoItemReader)
            .processor(asyncPostInfoBloomFilterItemProcessor)
            .writer(asyncPostInfoBloomFilterItemWriter)
            .taskExecutor(taskExecutor)
            .build()
    }

    @Bean
    fun postInfoBloomFilterItemProcessor(): ItemProcessor<Post, String> {
        return PostInfoBloomFilterItemProcessor()
    }

    @Bean
    fun asyncPostInfoBloomFilterItemProcessor(
        postInfoBloomFilterItemProcessor: ItemProcessor<Post, String>,
        taskExecutor: TaskExecutor
    ): AsyncItemProcessor<Post, String> {
        val asyncItemProcessor = AsyncItemProcessor<Post, String>()
        asyncItemProcessor.setDelegate(postInfoBloomFilterItemProcessor)
        asyncItemProcessor.setTaskExecutor(taskExecutor)
        return asyncItemProcessor
    }

    @Bean
    fun asyncPostInfoBloomFilterItemWriter(
        postInfoBloomFilterItemWriter: RedisBloomFilterItemWriter<String>
    ): AsyncItemWriter<String> {
        val asyncWriter = AsyncItemWriter<String>()
        asyncWriter.setDelegate(postInfoBloomFilterItemWriter)
        return asyncWriter
    }

    @Bean
    fun postInfoBloomFilterItemWriter(
        titleBloomFilter: RBloomFilter<String>): RedisBloomFilterItemWriter<String> {
        val redisBloomFilterItemWriter = RedisBloomFilterItemWriter<String>()
        redisBloomFilterItemWriter.setBloomFilter(titleBloomFilter)
        redisBloomFilterItemWriter.afterPropertiesSet()
        return redisBloomFilterItemWriter
    }
}
