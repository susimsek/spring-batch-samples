package io.github.susimsek.springbatchsamples.repository

import io.github.susimsek.springbatchsamples.domain.Post
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository :
    ListCrudRepository<Post, String>,
    PagingAndSortingRepository<Post, String>
