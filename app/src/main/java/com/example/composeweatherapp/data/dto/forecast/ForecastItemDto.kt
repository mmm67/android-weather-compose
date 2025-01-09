package com.example.composeweatherapp.data.dto.forecast

import com.example.composeweatherapp.data.dto.currentweather.CloudsDto
import com.example.composeweatherapp.data.dto.currentweather.MainDto
import com.example.composeweatherapp.data.dto.currentweather.RainDto
import com.example.composeweatherapp.data.dto.currentweather.SysDto
import com.example.composeweatherapp.data.dto.currentweather.WeatherDto
import com.example.composeweatherapp.data.dto.currentweather.WindDto
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastItemDto(
    val clouds: CloudsDto?,
    val dt: Int,
    @SerializedName("dt_txt")
    val dtText: String,
    val main: MainDto,
    @SerializedName("pop")
    val pop: Double,
    val rain: RainDto?,
    val sys: SysDto?,
    val visibility: Int,
    val weather: List<WeatherDto>,
    val wind: WindDto?
)