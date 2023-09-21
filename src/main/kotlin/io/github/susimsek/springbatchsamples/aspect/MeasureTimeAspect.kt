package io.github.susimsek.springbatchsamples.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@Aspect
@Component
class MeasureTimeAspect {

    private val log = LoggerFactory.getLogger(javaClass)

    @Around("@annotation(io.github.susimsek.springbatchsamples.aspect.MeasureTime)")
    @Throws(Throwable::class)
    fun measureTime(point: ProceedingJoinPoint): Any {
        val stopWatch = StopWatch()
        stopWatch.start()
        val `object` = point.proceed()
        stopWatch.stop()
        log.info(
            "Time take by " + point.signature.name + "() method is " +
                stopWatch.totalTimeNanos + " ns"
        )
        return `object`
    }
}
