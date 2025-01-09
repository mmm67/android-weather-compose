package com.example.composeweatherapp.domain.entities

data class ForecastResult(
    val todayForecast: List<ForecastItem>,
    val daysForecast: List<DailyForecastItem>
)
