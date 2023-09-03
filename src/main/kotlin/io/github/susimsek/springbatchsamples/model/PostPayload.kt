package io.github.susimsek.springbatchsamples.model

import io.github.susimsek.springbatchsamples.domain.enumerated.PostStatus
import java.io.Serializable
import java.time.OffsetDateTime

data class PostPayload(
    val id: String,

    val title: String,

    val content: String,

    val status: PostStatus,

    val createdAt: OffsetDateTime

) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}
