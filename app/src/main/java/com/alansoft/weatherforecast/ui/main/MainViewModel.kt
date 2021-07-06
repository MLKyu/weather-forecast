package com.alansoft.weatherforecast.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alansoft.weatherforecast.data.Resource
import com.alansoft.weatherforecast.data.response.SealedViewHolderData
import com.alansoft.weatherforecast.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private var job: Job? = null

    val isLoading = MutableLiveData<Boolean>()
    private val _result = MutableLiveData<List<SealedViewHolderData>>()
    val result: LiveData<List<SealedViewHolderData>> = _result

    private val locations =
        mutableListOf<SealedViewHolderData>().apply {
            add(SealedViewHolderData.HeaderItem())
        }

    fun loadWeather() {
        job?.cancel()
        job = viewModelScope.launch {
            repository.getSEWeather(onLoading = { b -> isLoading.postValue(b) })
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data
                                .flatMap { location ->
                                    listOf(
                                        SealedViewHolderData.WeatherItem(
                                            location.title,
                                            location.woeId
                                        )
                                    )
                                }.asFlow()
                                .onEach { each ->
                                    if (locations.find { (it as? SealedViewHolderData.WeatherItem)?.woeId == each.woeId } == null) {
                                        locations.add(each)
                                    }
                                }
                                .flatMapMerge { sealed ->
                                    repository.getLocation(
                                        { b -> isLoading.postValue(b) },
                                        sealed.woeId
                                    )
                                }.collect { locationRes ->
                                    when (locationRes) {
                                        is Resource.Success -> {
                                            locations.filter { (it as? SealedViewHolderData.WeatherItem)?.woeId == locationRes.data.woeId }
                                                .map {
                                                    (it as? SealedViewHolderData.WeatherItem)?.weather =
                                                        locationRes.data.consolidatedWeather
                                                }
                                            _result.postValue(locations.toList())
                                        }
                                        is Resource.Empty -> {

                                        }
                                        is Resource.Error -> {
                                        }
                                    }
                                }
                        }
                        is Resource.Empty -> {
                        }
                        is Resource.Error -> {
                        }
                    }
                }
        }
    }
}