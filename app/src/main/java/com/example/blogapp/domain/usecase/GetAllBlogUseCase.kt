package com.example.blogapp.domain.usecase

import android.content.Context
import com.example.blogapp.domain.model.Blog
import com.example.blogapp.domain.repository.BlogRepository
import com.example.blogapp.domain.util.NetworkUtil.Companion.hasInternetConnection
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllBlogUseCase @Inject constructor(
    private val repository: BlogRepository,
    @ApplicationContext private val context: Context
){
    suspend operator fun invoke() : List<Blog> {
        return withContext(Dispatchers.IO){
            if (repository.getBlogList().isEmpty()) {
                val networkBlogList = repository.fetchBlogList()
                repository.insertBlogList(networkBlogList)
            }
            if (hasInternetConnection(context = context)){
                return@withContext repository.fetchBlogList()
            } else {
                return@withContext repository.getBlogList()
            }
        }
    }
}