package com.example.composeweatherapp.data.dto.currentweather

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class WindDto(
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("gust")
    val gust: Double,
    @SerializedName("speed")
    val speed: Double
)