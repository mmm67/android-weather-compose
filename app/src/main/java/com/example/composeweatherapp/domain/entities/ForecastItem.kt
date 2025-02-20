package com.example.composeweatherapp.domain.entities

data class ForecastItem(
    val clouds: Clouds?,
    val dt: Int,
    val dtText: String,
    val main: Main,
    val pop: Double,
    val rain: Rain?,
    val sys: Sys?,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind?
)