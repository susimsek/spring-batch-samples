package io.github.susimsek.springbatchsamples.mapper

interface EntityMapper<E, D> {

    fun toDto(entity: E): D

    fun toDtoList(entityList: MutableList<E>): MutableList<D>
}
