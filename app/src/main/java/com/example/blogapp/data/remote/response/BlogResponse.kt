package com.example.blogapp.data.remote.response

data class BlogResponse<out T>(
    val code: Int,
    val results: T
)