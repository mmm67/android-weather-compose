package com.example.composeweatherapp.remote

import javax.inject.Inject

class WeatherDataSource @Inject constructor(
    private val weatherApi: WeatherApi,
    private val apiKey: String
) {
    suspend fun getGeoCoding(city: String, limit: Int = 5) =
        weatherApi.getGeocoding(city, limit, apiKey)

    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        units: String = "metric"
    ) = weatherApi.getCurrentWeather(lat, lon, apiKey, units)

    suspend fun getForecast(
        lat: Double,
        lon: Double,
        units: String = "metric"
    ) = weatherApi.getForecast(lat, lon, apiKey, units)

}