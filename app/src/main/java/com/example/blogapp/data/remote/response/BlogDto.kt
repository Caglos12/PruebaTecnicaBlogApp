package com.example.blogapp.data.remote.response

data class BlogDto(
    val id: Int,
    val titulo: String,
    val autor: String,
    val fecha: String,
    val contenido: String,
    val status: Int
)