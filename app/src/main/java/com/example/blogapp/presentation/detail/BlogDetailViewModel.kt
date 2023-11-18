package com.example.blogapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.domain.model.Blog
import com.example.blogapp.domain.usecase.GetSingleBlogUseCase
import com.example.blogapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogDetailViewModel @Inject constructor(
    private val getSingleBlogUseCase: GetSingleBlogUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _blogDetail : MutableLiveData<Resource<Blog>> = MutableLiveData()
    val blogDetail : LiveData<Resource<Blog>>
        get() = _blogDetail

    init {
        val blogId = savedStateHandle.get<Int>("blogId")
        blogId?.let { getSingleBlogById(blogId = it)}
    }

    private fun getSingleBlogById(blogId: Int) {
        viewModelScope.launch {
            getSingleBlogUseCase(id = blogId).onEach { state ->
                _blogDetail.value  = state
            }.launchIn(viewModelScope)
        }
    }
}