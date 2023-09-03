package io.github.susimsek.springbatchsamples.listener

import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.stereotype.Component


@Component
class JobCompletionNotificationListener : JobExecutionListener {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun beforeJob(jobExecution: JobExecution) {
        log.info("!!! JOB STARTED!")
    }

    override fun afterJob(jobExecution: JobExecution) {
        if (jobExecution.status === BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results")
        }
    }
}
