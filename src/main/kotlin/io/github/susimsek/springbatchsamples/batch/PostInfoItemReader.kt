package io.github.susimsek.springbatchsamples.batch

import io.github.susimsek.springbatchsamples.domain.Post
import io.github.susimsek.springbatchsamples.repository.PostRepository
import org.springframework.batch.item.data.RepositoryItemReader
import org.springframework.data.domain.Sort

class PostInfoItemReader(pageSize: Int, postRepository: PostRepository) : RepositoryItemReader<Post>() {

    private val sorts: Map<String, Sort.Direction> = mapOf("createdAt" to Sort.Direction.ASC)

    init {
        name = javaClass.name
        setRepository(postRepository)
        setSort(sorts)
        setMethodName("findAll")
        setPageSize(pageSize)
        setMaxItemCount(pageSize)
        afterPropertiesSet()
    }
}
