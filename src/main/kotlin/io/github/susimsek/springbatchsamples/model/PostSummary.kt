package io.github.susimsek.springbatchsamples.model

import java.io.Serializable

data class PostSummary(
    val id: String,

    val title: String

) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}
