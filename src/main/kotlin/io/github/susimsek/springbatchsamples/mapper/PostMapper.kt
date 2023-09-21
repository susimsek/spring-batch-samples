package io.github.susimsek.springbatchsamples.mapper

import io.github.susimsek.springbatchsamples.domain.Post
import io.github.susimsek.springbatchsamples.model.PostPayload
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
interface PostMapper : EntityMapper<Post, PostPayload> {
}
