package com.alansoft.weatherforecast.data.response

import com.google.gson.annotations.SerializedName

/**
 * Created by LEE MIN KYU on 2021/07/04
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
data class LocationResponse(
    var woeId: Long,
    @SerializedName("consolidated_weather")
    val consolidatedWeather: List<ConsolidatedWeather>
)

data class ConsolidatedWeather(
    @SerializedName("weather_state_name")
    val weatherStateName: String,   // 날씨 요약
    @SerializedName("weather_state_abbr")
    val weatherStateAbbr: String,   // 아이콘 이미지 정보 https://www.metaweather.com/static/img/weather/png/64/lr.png
    @SerializedName("the_temp")
    val theTemp: Float,               // 현재 날씨
    val humidity: Float,            // 습도
    @SerializedName("applicable_date")
    val applicableDate: String
) {
    fun getTheTempString(): String = String.format("%02d ℃", theTemp.toInt())
    fun getHumidityString(): String = String.format("%02d %%", humidity.toInt())
}