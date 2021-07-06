package com.alansoft.weatherforecast.data

import com.alansoft.weatherforecast.data.api.LocationApi
import com.alansoft.weatherforecast.data.response.LocationResponse
import com.alansoft.weatherforecast.data.response.LocationWeather
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/07/04
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class SearchDataSource @Inject constructor(private val locationApi: LocationApi) {
    suspend fun searchLocation(): List<LocationWeather> {
        return locationApi.searchLocation("SE")
    }

    suspend fun getLocation(woeId: Long): LocationResponse {
        return locationApi.location(woeId)
    }
}