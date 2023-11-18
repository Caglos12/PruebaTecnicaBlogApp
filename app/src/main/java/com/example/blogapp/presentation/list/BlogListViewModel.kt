package com.example.blogapp.presentation.list

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.domain.model.Blog
import com.example.blogapp.domain.usecase.GetAllBlogUseCase
import com.example.blogapp.domain.usecase.GetBlogByFilterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogListViewModel @Inject constructor(
    private val getAllBlogUseCase: GetAllBlogUseCase,
    private val getBlogByFilterUseCase: GetBlogByFilterUseCase
): ViewModel(){

    private val _blogList: MutableLiveData<List<Blog>> = MutableLiveData()
    val blogList : LiveData<List<Blog>>
        get() = _blogList

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    @SuppressLint("NullSafeMutableLiveData")
    fun getAllBlog(){
        viewModelScope.launch {
            _isLoading.value = true
            _blogList.value = getAllBlogUseCase()
            _isLoading.value = false
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun getBlogByFilter(filer: String) {
        viewModelScope.launch {
            _blogList.value
            _isLoading.value = true
            _blogList.value = getBlogByFilterUseCase(filer)
            _isLoading.value = false
        }
    }
}