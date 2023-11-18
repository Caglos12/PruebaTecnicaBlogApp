package com.example.blogapp.data.remote.api

import com.example.blogapp.data.remote.constants.RetrofitConstants
import com.example.blogapp.data.remote.response.BlogDto
import com.example.blogapp.data.remote.response.BlogResponse
import com.example.blogapp.data.remote.response.RegisterResponse
import com.example.blogapp.data.remote.response.RequestBodies
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BlogApi {

    @GET(RetrofitConstants.LIST_BLOG)
    suspend fun fetchBlogs(): Response<BlogResponse<List<BlogDto>>>

    @GET(RetrofitConstants.BLOG_DETAIL)
    suspend fun fetchSingleBlog(
        @Query("id") id: Int
    ): BlogDto

    @GET(RetrofitConstants.LIST_FOR_FILTER)
    suspend fun fetchBlogsForFilter(
        @Query("filtro") filter: String
    ): Response<BlogResponse<List<BlogDto>>>

    @POST(RetrofitConstants.ADD_BLOG)
    suspend fun registerBlog(
        @Body body: RequestBodies.BlogBody
    ): Response<RegisterResponse>
}