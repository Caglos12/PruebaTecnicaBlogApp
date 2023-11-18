package com.example.blogapp.di

import android.content.Context
import androidx.room.Room
import com.example.blogapp.data.local.constants.RoomConstants
import com.example.blogapp.data.local.database.BlogDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideBlogDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, BlogDatabase::class.java, RoomConstants.BLOG_DATABASE).build()

    @Provides
    @Singleton
    fun provideBlogDao(db: BlogDatabase) = db.blogDao()
}