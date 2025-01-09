package com.example.composeweatherapp.presentation

import com.example.composeweatherapp.domain.DataError
import com.example.composeweatherapp.domain.entities.CurrentWeather
import com.example.composeweatherapp.domain.entities.ForecastResult
import com.example.composeweatherapp.domain.entities.GeoCodingItem

data class WeatherUiState(
    val geoCodingResult: List<GeoCodingItem>? = null,
    val currentWeatherResult: CurrentWeather? = null,
    val forecastResult: ForecastResult? = null,
    val forecastError: DataError? = null,
    val geoCodingError: DataError? = null,
    val currentWeatherError: DataError? = null,
    val isLoading: Boolean = false
)