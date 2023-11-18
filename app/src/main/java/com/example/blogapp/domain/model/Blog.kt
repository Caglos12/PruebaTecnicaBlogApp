package com.example.blogapp.domain.model

data class Blog(
    val id: Int,
    val titulo: String,
    val autor: String,
    val fecha: String,
    val contenido: String,
    val status: Int
)
