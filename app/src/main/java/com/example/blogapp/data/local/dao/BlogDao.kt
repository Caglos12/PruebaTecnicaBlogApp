package com.example.blogapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.blogapp.data.local.constants.RoomConstants
import com.example.blogapp.data.local.entity.BlogEntity

@Dao
interface BlogDao {

    @Query("SELECT * FROM ${RoomConstants.BLOG_TABLE}")
    fun getAllBlogs(): List<BlogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBlog(vararg blog: BlogEntity)

    @Query("SELECT * FROM ${RoomConstants.BLOG_TABLE} WHERE id = :id")
    fun getSingleBlogById(id: Int) : BlogEntity

    @Query("SELECT * FROM ${RoomConstants.BLOG_TABLE} WHERE titulo LIKE '%' || :filter || '%' " +
            "OR autor LIKE '%' || :filter || '%' OR contenido LIKE '%' || :filter || '%'")
    fun getBlogListByFilter(filter: String): List<BlogEntity>
}