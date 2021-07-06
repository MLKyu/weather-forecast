package com.alansoft.weatherforecast.data.api

import com.alansoft.weatherforecast.data.response.LocationResponse
import com.alansoft.weatherforecast.data.response.LocationWeather
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by LEE MIN KYU on 2021/07/04
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
interface LocationApi {
    @GET("/api/location/search/")
    suspend fun searchLocation(@Query("query") query: String): List<LocationWeather>

    @GET("/api/location/{woeid}")
    suspend fun location(@Path("woeid") woeId: Long): LocationResponse
}