package com.example.composeweatherapp.data.dto.currentweather

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)