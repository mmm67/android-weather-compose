package com.example.composeweatherapp.domain.entities

data class CurrentWeather(
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val wind: Wind?,
    val rain: Rain?,
    val clouds: Clouds?,
    val sys: Sys?,
    val timezone: Int,
    val id: Int,
    val name: String,
)