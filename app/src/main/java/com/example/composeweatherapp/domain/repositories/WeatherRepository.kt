package com.example.composeweatherapp.domain.repositories

import com.example.composeweatherapp.domain.DataError
import com.example.composeweatherapp.domain.Result
import com.example.composeweatherapp.domain.entities.CurrentWeather
import com.example.composeweatherapp.domain.entities.Forecast
import com.example.composeweatherapp.domain.entities.GeoCodingItem

interface WeatherRepository {
    suspend fun getGeoCoding(
        city: String,
        limit: Int = 5
    ): Result<List<GeoCodingItem>, DataError>

    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        units: String = "metric"
    ): Result<CurrentWeather, DataError>

    suspend fun getForecast(
        lat: Double,
        lon: Double,
        units: String = "metric"
    ): Result<Forecast, DataError>
}