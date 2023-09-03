package io.github.susimsek.springbatchsamples.config

import io.github.susimsek.springbatchsamples.domain.Post
import io.github.susimsek.springbatchsamples.listener.JobCompletionNotificationListener
import io.github.susimsek.springbatchsamples.repository.PostRepository
import io.github.susimsek.springbatchsamples.batch.PostInfoItemProcessor
import io.github.susimsek.springbatchsamples.batch.PostInfoItemReader
import io.github.susimsek.springbatchsamples.batch.PostInfoItemWriter
import io.github.susimsek.springbatchsamples.model.PostPayload
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.integration.async.AsyncItemProcessor
import org.springframework.batch.integration.async.AsyncItemWriter
import org.springframework.batch.item.data.RepositoryItemReader
import org.springframework.batch.item.kafka.KafkaItemWriter
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.transaction.PlatformTransactionManager
import java.util.concurrent.Future
import java.util.concurrent.ThreadPoolExecutor

@Configuration
class PostInfoJobConfig {

    @Bean
    fun postInfoJob(
        jobRepository: JobRepository,
        postInfoStep: Step,
        listener: JobCompletionNotificationListener
    ): Job {
        return JobBuilder("postInfoJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .listener(listener)
            .start(postInfoStep)
            .build()
    }

    @Bean
    @Suppress("LongParameterList")
    fun postInfoStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        taskExecutor: TaskExecutor,
        postInfoItemReader: RepositoryItemReader<Post>,
        asyncPostInfoItemProcessor: AsyncItemProcessor<Post, PostPayload>,
        asyncPostInfoItemWriter: AsyncItemWriter<PostPayload>
    ): Step {
        return StepBuilder("postInfoStep", jobRepository)
            .chunk<Post, Future<PostPayload>>(10, transactionManager)
            .reader(postInfoItemReader)
            .processor(asyncPostInfoItemProcessor)
            .writer(asyncPostInfoItemWriter)
            .taskExecutor(taskExecutor)
            .build()
    }

    @Bean
    fun taskExecutor(taskExecutionProperties: TaskExecutionProperties): TaskExecutor {
        val executor = ThreadPoolTaskExecutor().apply {
            corePoolSize = taskExecutionProperties.pool.coreSize
            maxPoolSize = taskExecutionProperties.pool.maxSize
            queueCapacity = taskExecutionProperties.pool.queueCapacity
            threadNamePrefix = taskExecutionProperties.threadNamePrefix
        }
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
        executor.initialize()
        return executor
    }

    @Bean
    fun postInfoItemReader(postRepository: PostRepository): RepositoryItemReader<Post> {
        return PostInfoItemReader(10, postRepository)
    }

    @Bean
    fun postInfoItemWriter(kafkaTemplate: KafkaTemplate<String, PostPayload>): KafkaItemWriter<String, PostPayload> {
        return PostInfoItemWriter(kafkaTemplate)
    }

    @Bean
    fun asyncPostInfoItemProcessor(
        postInfoItemProcessor: PostInfoItemProcessor,
        taskExecutor: TaskExecutor
    ): AsyncItemProcessor<Post, PostPayload> {
        val asyncItemProcessor = AsyncItemProcessor<Post, PostPayload>()
        asyncItemProcessor.setDelegate(postInfoItemProcessor)
        asyncItemProcessor.setTaskExecutor(taskExecutor)
        return asyncItemProcessor
    }

    @Bean
    fun asyncPostInfoItemWriter(
        postInfoItemWriter: KafkaItemWriter<String, PostPayload>
    ): AsyncItemWriter<PostPayload> {
        val asyncWriter = AsyncItemWriter<PostPayload>()
        asyncWriter.setDelegate(postInfoItemWriter)
        return asyncWriter
    }
}
