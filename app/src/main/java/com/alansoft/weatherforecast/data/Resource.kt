package com.alansoft.weatherforecast.data

import java.io.IOException

/**
 * Created by LEE MIN KYU on 2021/07/04
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable) : Resource<Nothing>() {
        val isNetworkError: Boolean get() = exception is IOException
    }

    object Empty : Resource<Nothing>()
    companion object {
        fun <T> success(data: T) = Success(data)
        fun error(exception: Throwable) = Error(exception)
        fun empty() = Empty
        fun <T> pushAndSuccess(data: T, block: (data: T) -> Unit): Resource<T> {
            block(data)
            return Success(data)
        }
    }
}