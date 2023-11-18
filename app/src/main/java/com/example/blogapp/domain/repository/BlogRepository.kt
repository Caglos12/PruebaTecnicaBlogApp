package com.example.blogapp.domain.repository

import com.example.blogapp.data.remote.response.RequestBodies
import com.example.blogapp.domain.model.Blog
import com.example.blogapp.domain.util.Resource

interface BlogRepository {

    suspend fun fetchBlogList() : List<Blog>

    suspend fun fetchSingleBlog(id: Int): Resource<Blog>

    suspend fun fetchBlogByFilter(filter: String): List<Blog>

    suspend fun insertBlogList(blogList: List<Blog>)

    suspend fun getBlogList(): List<Blog>

    suspend fun fetchSingleBlogDao(id: Int)  :Resource<Blog>

    suspend fun getBLogListByFilter(filter: String): List<Blog>

}