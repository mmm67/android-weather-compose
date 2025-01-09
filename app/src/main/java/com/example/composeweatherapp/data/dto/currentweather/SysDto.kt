package com.example.composeweatherapp.data.dto.currentweather

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SysDto(
    @SerializedName("country")
    val country: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("type")
    val type: Int
)