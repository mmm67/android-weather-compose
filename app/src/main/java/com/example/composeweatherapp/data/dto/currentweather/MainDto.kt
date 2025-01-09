package com.example.composeweatherapp.data.dto.currentweather

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MainDto(
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("grnd_level")
    val groundLevel: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("sea_level")
    val seaLevel: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double?,
    @SerializedName("temp_min")
    val tempMin: Double?
)