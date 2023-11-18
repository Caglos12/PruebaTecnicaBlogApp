package com.example.blogapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.blogapp.data.local.dao.BlogDao
import com.example.blogapp.data.local.entity.BlogEntity

@Database(entities = [BlogEntity::class], version = 2)
abstract class BlogDatabase : RoomDatabase(){
    abstract fun blogDao(): BlogDao
}