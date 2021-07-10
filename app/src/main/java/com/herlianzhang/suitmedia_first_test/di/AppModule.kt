package com.herlianzhang.suitmedia_first_test.di

import com.herlianzhang.suitmedia_first_test.BuildConfig
import com.herlianzhang.suitmedia_first_test.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideApiService(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(
        okHttpClient,
        converterFactory,
        ApiService::class.java
    )

    private fun <T> provideService(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        clazz: Class<T>,
        baseUrl: String = BuildConfig.BASE_URL,
    ): T = createRetrofit(baseUrl, okHttpClient, converterFactory).create(clazz)

    private fun createRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()

}