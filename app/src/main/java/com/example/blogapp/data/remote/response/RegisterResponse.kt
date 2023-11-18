package com.example.blogapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @SerializedName("code") val code: Int
)