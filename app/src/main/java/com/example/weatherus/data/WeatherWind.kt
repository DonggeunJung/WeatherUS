package com.example.weatherus.data

import kotlinx.serialization.Serializable

@Serializable
data class WeatherWind(var speed: Double, var deg: Int)