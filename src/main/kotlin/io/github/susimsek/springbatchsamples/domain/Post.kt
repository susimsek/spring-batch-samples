package io.github.susimsek.springbatchsamples.domain

import io.github.susimsek.springbatchsamples.domain.enumerated.PostStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "post")
data class Post(

    var title: String = "",

    var content: String = "",

    @Enumerated(EnumType.STRING)
    var status: PostStatus? = PostStatus.DRAFT
) : BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Post) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return "Post(title=$title, content=$content, status=$status)"
    }
}
