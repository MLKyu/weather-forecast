package com.alansoft.weatherforecast.data.response

import com.google.gson.annotations.SerializedName

/**
 * Created by LEE MIN KYU on 2021/07/04
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
data class LocationWeather(
    val title: String,
    @SerializedName("woeid")
    val woeId: Long
)