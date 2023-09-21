package io.github.susimsek.springbatchsamples.config

import io.github.susimsek.springbatchsamples.batch.PostInfoBloomFilterItemProcessor
import io.github.susimsek.springbatchsamples.batch.PostInfoBloomFilterItemReader
import io.github.susimsek.springbatchsamples.batch.RedisBloomFilterItemWriter
import io.github.susimsek.springbatchsamples.listener.JobCompletionNotificationListener
import io.github.susimsek.springbatchsamples.model.PostTitle
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
import org.springframework.batch.item.ItemReader
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
        postInfoBloomFilterItemReader: ItemReader<PostTitle>,
        asyncPostInfoBloomFilterItemProcessor: AsyncItemProcessor<PostTitle, PostTitle>,
        asyncPostInfoBloomFilterItemWriter: AsyncItemWriter<PostTitle>
    ): Step {
        return StepBuilder("postInfoBloomFilterStep", jobRepository)
            .chunk<PostTitle, Future<PostTitle>>(100, transactionManager)
            .reader(postInfoBloomFilterItemReader)
            .processor(asyncPostInfoBloomFilterItemProcessor)
            .writer(asyncPostInfoBloomFilterItemWriter)
            .taskExecutor(taskExecutor)
            .build()
    }

    @Bean
    fun postInfoBloomFilterItemReader(): ItemReader<PostTitle> {
        return PostInfoBloomFilterItemReader()
    }

    @Bean
    fun postInfoBloomFilterItemProcessor(): ItemProcessor<PostTitle, PostTitle> {
        return PostInfoBloomFilterItemProcessor()
    }

    @Bean
    fun asyncPostInfoBloomFilterItemProcessor(
        postInfoBloomFilterItemProcessor: ItemProcessor<PostTitle, PostTitle>,
        taskExecutor: TaskExecutor
    ): AsyncItemProcessor<PostTitle, PostTitle> {
        val asyncItemProcessor = AsyncItemProcessor<PostTitle, PostTitle>()
        asyncItemProcessor.setDelegate(postInfoBloomFilterItemProcessor)
        asyncItemProcessor.setTaskExecutor(taskExecutor)
        return asyncItemProcessor
    }

    @Bean
    fun asyncPostInfoBloomFilterItemWriter(
        postInfoBloomFilterItemWriter: RedisBloomFilterItemWriter<PostTitle>
    ): AsyncItemWriter<PostTitle> {
        val asyncWriter = AsyncItemWriter<PostTitle>()
        asyncWriter.setDelegate(postInfoBloomFilterItemWriter)
        return asyncWriter
    }

    @Bean
    fun postInfoBloomFilterItemWriter(
        postSummaryBloomFilter: RBloomFilter<PostTitle>
    ): RedisBloomFilterItemWriter<PostTitle> {
        val redisBloomFilterItemWriter = RedisBloomFilterItemWriter<PostTitle>()
        redisBloomFilterItemWriter.setBloomFilter(postSummaryBloomFilter)
        redisBloomFilterItemWriter.afterPropertiesSet()
        return redisBloomFilterItemWriter
    }
}
