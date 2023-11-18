package com.example.blogapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BlogEntity (
    @PrimaryKey
    val id: Int,
    val titulo: String,
    val autor: String,
    val fecha: String,
    val contenido: String,
    val status: Int
)