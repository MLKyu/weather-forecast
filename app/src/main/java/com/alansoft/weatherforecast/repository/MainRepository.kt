package com.alansoft.weatherforecast.repository

import androidx.annotation.WorkerThread
import com.alansoft.weatherforecast.data.Resource
import com.alansoft.weatherforecast.data.SearchDataSource
import com.alansoft.weatherforecast.data.response.LocationResponse
import com.alansoft.weatherforecast.data.response.LocationWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/07/04
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class MainRepository @Inject constructor(private val remote: SearchDataSource) {

    @WorkerThread
    fun getSEWeather(
        onLoading: (Boolean) -> Unit
    ): Flow<Resource<List<LocationWeather>>> = flow {
        val response = remote.searchLocation()
        if (response.isNullOrEmpty()) {
            emit(Resource.empty())
        } else {
            emit(Resource.success(response))
        }
    }.onStart {
        onLoading(true)
    }.onCompletion {
        onLoading(false)
    }.retry(2) { cause ->
        cause is IOException
    }.catch { e ->
        emit(Resource.error(e))
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getLocation(
        onLoading: (Boolean) -> Unit,
        woeId: Long
    ): Flow<Resource<LocationResponse>> = flow {
        val response = remote.getLocation(woeId)
        if (response.consolidatedWeather.isNullOrEmpty()) {
            emit(Resource.empty())
        } else {
            emit(Resource.success(response.apply { this.woeId = woeId }))
        }
    }.onStart {
        onLoading(true)
    }.onCompletion {
        onLoading(false)
    }.retry(2) { cause ->
        cause is IOException
    }.catch { e ->
        emit(Resource.error(e))
    }.flowOn(Dispatchers.IO)
}