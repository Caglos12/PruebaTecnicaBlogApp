package com.example.blogapp.data.mapper

import com.example.blogapp.data.local.entity.BlogEntity
import com.example.blogapp.data.remote.response.BlogDto
import com.example.blogapp.domain.model.Blog

fun BlogDto.mapToDomain(): Blog {
    return Blog(
        id = id,
        titulo = titulo,
        autor = autor,
        fecha = fecha,
        contenido = contenido,
        status = status
    )
}

fun BlogEntity.mapToDomain(): Blog {
    return Blog(
        id = id,
        titulo = titulo,
        autor = autor,
        fecha = fecha,
        contenido = contenido,
        status = status
    )
}

fun Blog.mapToEntity(): BlogEntity {
    return BlogEntity(
        id = id,
        titulo = titulo,
        autor = autor,
        fecha = fecha,
        contenido = contenido,
        status = status
    )
}