package com.example.composeweatherapp.data.dto.geocoding

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GeoCodingItemDto(
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("state")
    val state: String
)