package com.example.blogapp.di

import com.example.blogapp.data.local.dao.BlogDao
import com.example.blogapp.data.remote.api.BlogApi
import com.example.blogapp.data.repository.BlogRepositoryImpl
import com.example.blogapp.domain.repository.BlogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideBlogRepository(
        blogDao: BlogDao,
        blogApi: BlogApi
    ) : BlogRepository = BlogRepositoryImpl(blogDao, blogApi)
}