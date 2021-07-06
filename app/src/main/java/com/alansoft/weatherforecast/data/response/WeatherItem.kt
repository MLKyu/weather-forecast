package com.alansoft.weatherforecast.data.response

/**
 * Created by LEE MIN KYU on 2021/07/04
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
sealed class SealedViewHolderData {
    data class WeatherItem(
        val location: String,
        val woeId: Long,
        var weather: List<ConsolidatedWeather> = mutableListOf()
    ) : SealedViewHolderData()

    data class HeaderItem(val text: List<String> = listOf("Local", "Today", "Tomorrow")) :
        SealedViewHolderData()
}

