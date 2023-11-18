package com.example.blogapp.domain.usecase

import android.content.Context
import com.example.blogapp.domain.model.Blog
import com.example.blogapp.domain.repository.BlogRepository
import com.example.blogapp.domain.util.NetworkUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBlogByFilterUseCase @Inject constructor(
    private val repository: BlogRepository,
    @ApplicationContext private val context: Context
){
    suspend operator fun invoke(filter: String): List<Blog> {
        return withContext(Dispatchers.IO){
            if (NetworkUtil.hasInternetConnection(context = context)){
                return@withContext repository.fetchBlogByFilter(filter)
            } else {
                return@withContext repository.getBLogListByFilter(filter)
            }
        }
    }
}