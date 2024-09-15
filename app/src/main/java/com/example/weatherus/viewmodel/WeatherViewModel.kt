package com.example.weatherus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherus.data.State
import com.example.weatherus.data.Weather
import com.example.weatherus.domain.StateRepository
import com.example.weatherus.domain.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val stateRepository: StateRepository, private val weatherRepository: WeatherRepository) : ViewModel() {
    private val ldStates = MutableLiveData<List<State>>()
    val states: LiveData<List<State>> = ldStates
    private val ldWeather = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = ldWeather

    // Request Card data list to Repository
    fun reqStates() {
        viewModelScope.launch {
            val res = stateRepository.reqStates()

            withContext(Dispatchers.Main) {
                res?.let { ldStates.postValue(it) }
            }
        }
    }

    // Request Weather data by City name
    fun reqWeather(city: String) {
        viewModelScope.launch {
            val res = weatherRepository.reqWeather(city)

            withContext(Dispatchers.Main) {
                res?.let { ldWeather.postValue(it) }
            }
        }
    }

    // Request Weather data by location
    fun reqWeatherLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            val res = weatherRepository.reqWeatherLocation(lat, lon)

            withContext(Dispatchers.Main) {
                res?.let { ldWeather.postValue(it) }
            }
        }
    }

}