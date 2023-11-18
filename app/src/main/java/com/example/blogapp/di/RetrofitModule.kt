package com.example.blogapp.di

import com.example.blogapp.data.remote.api.BlogApi
import com.example.blogapp.data.remote.constants.RetrofitConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideBlogApi(
        client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) : BlogApi {
        return Retrofit.Builder()
            .baseUrl(RetrofitConstants.URL_BASE)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
            .create()
    }
}