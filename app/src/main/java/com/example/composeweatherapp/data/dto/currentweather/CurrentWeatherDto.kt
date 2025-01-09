package com.example.composeweatherapp.data.dto.currentweather

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDto(
    @SerializedName("weather")
    val weather: List<WeatherDto>,
    @SerializedName("base")
    val base: String,
    @SerializedName("main")
    val main: MainDto,
    @SerializedName("wind")
    val wind: WindDto?,
    @SerializedName("rain")
    val rain: RainDto?,
    @SerializedName("clouds")
    val clouds: CloudsDto?,
    @SerializedName("sys")
    val sys: SysDto?,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)