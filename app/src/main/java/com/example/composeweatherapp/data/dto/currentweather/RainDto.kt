package com.example.composeweatherapp.data.dto.currentweather

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class RainDto(
    @SerializedName("1h")
    val oneHour: Double
)