package com.example.blogapp.data.remote.response

object RequestBodies {
    data class BlogBody (
        val titulo: String,
        val autor: String,
        val fecha: String,
        val contenido: String
    )
}
