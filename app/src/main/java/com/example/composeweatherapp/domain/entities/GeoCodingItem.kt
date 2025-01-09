package com.example.composeweatherapp.domain.entities

data class GeoCodingItem(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String
)