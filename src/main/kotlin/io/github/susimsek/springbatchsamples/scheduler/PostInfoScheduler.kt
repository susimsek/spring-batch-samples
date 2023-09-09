package io.github.susimsek.springbatchsamples.scheduler

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class PostInfoScheduler(
    private val jobLauncher: JobLauncher,
    private val postInfoJob: Job
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(cron = "\${cron.expression.post-info}")
    @Throws(Exception::class)
    fun perform() {
        log.info("Job Started at : {}" + OffsetDateTime.now())
        val params = JobParametersBuilder()
            .addString("JobID", System.currentTimeMillis().toString())
            .toJobParameters()
        var execution = jobLauncher.run(postInfoJob, params)
        log.info("Job finished with status : {}" + execution.status)
    }
}
