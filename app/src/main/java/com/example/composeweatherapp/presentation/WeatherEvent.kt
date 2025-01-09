package com.example.composeweatherapp.presentation

sealed class WeatherEvent {
    data class GetCurrentWeather(val city: String) : WeatherEvent()
}