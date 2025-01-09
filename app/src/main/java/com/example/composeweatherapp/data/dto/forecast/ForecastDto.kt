package com.example.composeweatherapp.data.dto.forecast

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDto(
    @SerializedName("list")
    val forecastItems: List<ForecastItemDto>,
    val message: Int
)