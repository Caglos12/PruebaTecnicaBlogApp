package com.example.blogapp.data.repository

import android.util.Log
import com.example.blogapp.data.local.dao.BlogDao
import com.example.blogapp.data.mapper.mapToDomain
import com.example.blogapp.data.mapper.mapToEntity
import com.example.blogapp.data.remote.api.BlogApi
import com.example.blogapp.data.remote.response.RequestBodies
import com.example.blogapp.domain.model.Blog
import com.example.blogapp.domain.repository.BlogRepository
import com.example.blogapp.domain.util.Resource
import javax.inject.Inject

class BlogRepositoryImpl @Inject constructor(
    private val blogDao: BlogDao,
    private val blogApi: BlogApi
) : BlogRepository {

    override suspend fun fetchBlogList(): List<Blog> {
        return try {
            val request = blogApi.fetchBlogs()
            if (request.isSuccessful) {
                request.body()?.results?.map { it.mapToDomain() }?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception){
            Log.d("Test", e.toString())
            emptyList()
        }
    }

    override suspend fun fetchSingleBlog(id: Int): Resource<Blog> {
        return try {
            Resource.Success(
                data = blogApi.fetchSingleBlog(id).mapToDomain()
            )
        } catch (e: Exception){
            Resource.Error("Unknown error")
        }
    }

    override suspend fun fetchBlogByFilter(filter: String): List<Blog> {
        return try {
            val request = blogApi.fetchBlogsForFilter(filter)

            if (request.isSuccessful) {
                request.body()?.results?.map { it.mapToDomain() }?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception){
            Log.d("Test", e.toString())
            emptyList()
        }
    }

    override suspend fun insertBlogList(blogList: List<Blog>) {
        blogDao.insertBlog(*blogList.map { it.mapToEntity() }.toTypedArray())
    }

    override suspend fun getBlogList(): List<Blog> {
        return blogDao.getAllBlogs().map { it.mapToDomain() }
    }

    override suspend fun fetchSingleBlogDao(id: Int): Resource<Blog> {
        return try {
            Resource.Success(
                data = blogDao.getSingleBlogById(id).mapToDomain()
            )
        } catch (e: Exception){
            Resource.Error("This blog isn't registered in the database")
        }
    }

    override suspend fun getBLogListByFilter(filter: String): List<Blog> {
        return try {
            val request = blogDao.getBlogListByFilter(filter)

            if(request.isNotEmpty()) {
                request.map { it.mapToDomain() }
            } else {
                emptyList()
            }
        } catch (e: Exception){
            Log.d("Test", e.toString())
            emptyList()
        }
    }

    suspend fun registerBlog(body: RequestBodies.BlogBody) = blogApi.registerBlog(body)
}