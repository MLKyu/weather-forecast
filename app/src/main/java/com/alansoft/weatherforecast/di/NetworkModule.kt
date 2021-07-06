package com.alansoft.weatherforecast.di

import android.util.Log
import com.alansoft.weatherforecast.data.api.LocationApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by LEE MIN KYU on 2021/07/04
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val BASE_HOST = "https://www.metaweather.com/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .build()
                )
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(Interceptor {
                val originalRequest = it.request()
                val request = originalRequest.newBuilder().url(originalRequest.url).build()
                Log.d("OkHttp3", request.toString())
                it.proceed(request)
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_HOST)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .create()
                )
            )
            .client(client)
            .build()

    @Singleton
    @Provides
    fun provideLocationApi(retrofit: Retrofit): LocationApi =
        retrofit.create(LocationApi::class.java)
}