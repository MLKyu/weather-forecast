package com.alansoft.weatherforecast.ba

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alansoft.weatherforecast.data.response.SealedViewHolderData
import com.alansoft.weatherforecast.extension.loadWithThumbnail
import com.alansoft.weatherforecast.ui.main.WeatherAdapter

/**
 * Created by LEE MIN KYU on 2021/07/04
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
object BindingAdapter {
    @JvmStatic
    @BindingAdapter("loading")
    fun bindLoading(layout: SwipeRefreshLayout, loading: Boolean) {
        layout.isRefreshing = loading
    }

    @JvmStatic
    @BindingAdapter("loadImg")
    fun bindLoadImg(view: ImageView, url: String?) {

        val urlFormat = "https://www.metaweather.com/static/img/weather/png/64/url.png"
        val imgUrl = urlFormat.replace("url", url ?: return)
        view.loadWithThumbnail(imgUrl)
    }
}