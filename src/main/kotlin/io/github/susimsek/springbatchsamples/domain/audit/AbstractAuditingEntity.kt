package io.github.susimsek.springbatchsamples.domain.audit

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.Clock
import java.time.OffsetDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class AbstractAuditingEntity(

    @CreatedDate
    var createdAt: OffsetDateTime = OffsetDateTime.now(Clock.systemDefaultZone()),

    @LastModifiedDate
    var updatedAt: OffsetDateTime = OffsetDateTime.now(Clock.systemDefaultZone()),

    @Version
    var version: Long = 0
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
