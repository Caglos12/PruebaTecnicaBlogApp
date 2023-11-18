package com.example.blogapp.presentation.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.data.remote.response.RegisterResponse
import com.example.blogapp.data.remote.response.RequestBodies
import com.example.blogapp.data.repository.BlogRepositoryImpl
import com.example.blogapp.domain.util.Event
import com.example.blogapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BlogAddViewModel @Inject constructor(
    private val repository: BlogRepositoryImpl
) : ViewModel() {

    private val _registerResponse = MutableLiveData<Event<Resource<RegisterResponse>>>()
    val registerResponse: LiveData<Event<Resource<RegisterResponse>>> = _registerResponse

    fun registerBlog(body: RequestBodies.BlogBody) = viewModelScope.launch {
        register(body)
    }

    private suspend fun register(body: RequestBodies.BlogBody) {
        _registerResponse.postValue(Event(Resource.Loading))
        try {
            val response = repository.registerBlog(body)
            _registerResponse.postValue(handleResponse(response))
            Log.d("body_response: ", body.toString())
        } catch (t: Throwable) {
            when(t) {
                is IOException -> {
                    _registerResponse.postValue(
                        Event(Resource.Error("Failure"))
                    )
                } else -> {
                    _registerResponse.postValue(Event(Resource.Error(
                        "Conversion Error"
                    )))
                }
            }
        }
    }

    private fun handleResponse(response: Response<RegisterResponse>) : Event<Resource<RegisterResponse>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}