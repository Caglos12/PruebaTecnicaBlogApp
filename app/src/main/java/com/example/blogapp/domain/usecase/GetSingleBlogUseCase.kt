package com.example.blogapp.domain.usecase

import com.example.blogapp.domain.model.Blog
import com.example.blogapp.domain.repository.BlogRepository
import com.example.blogapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSingleBlogUseCase @Inject constructor(
    private val repository: BlogRepository
){
    suspend operator fun invoke(id: Int): Flow<Resource<Blog>> = flow {
        emit(Resource.Loading)
        val blogDetailState = repository.fetchSingleBlog(id = id)
        emit(blogDetailState)
    }
}