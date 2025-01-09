package com.example.composeweatherapp.remote

import com.example.composeweatherapp.data.dto.currentweather.CurrentWeatherDto
import com.example.composeweatherapp.data.dto.forecast.ForecastDto
import com.example.composeweatherapp.data.dto.geocoding.GeoCodingItemDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): CurrentWeatherDto

    @GET("data/2.5/forecast?")
    suspend fun getForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): ForecastDto


    @GET("geo/1.0/direct")
    suspend fun getGeocoding(
        @Query("q") city: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String
    ): List<GeoCodingItemDto>

}