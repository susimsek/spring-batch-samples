package io.github.susimsek.springbatchsamples.domain.audit

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class AbstractUserAuditingEntity(

    @CreatedBy
    var createdBy: String = "",

    @LastModifiedBy
    var lastModifiedBy: String = ""
) : AbstractAuditingEntity(), Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}
