package com.example.composeweatherapp.mappers

import com.example.composeweatherapp.domain.entities.Clouds
import com.example.composeweatherapp.domain.entities.CurrentWeather
import com.example.composeweatherapp.domain.entities.GeoCodingItem
import com.example.composeweatherapp.domain.entities.Main
import com.example.composeweatherapp.domain.entities.Rain
import com.example.composeweatherapp.domain.entities.Sys
import com.example.composeweatherapp.domain.entities.Weather
import com.example.composeweatherapp.domain.entities.Wind
import com.example.composeweatherapp.data.dto.currentweather.CloudsDto
import com.example.composeweatherapp.data.dto.currentweather.CurrentWeatherDto
import com.example.composeweatherapp.data.dto.geocoding.GeoCodingItemDto
import com.example.composeweatherapp.data.dto.currentweather.MainDto
import com.example.composeweatherapp.data.dto.currentweather.RainDto
import com.example.composeweatherapp.data.dto.currentweather.SysDto
import com.example.composeweatherapp.data.dto.currentweather.WeatherDto
import com.example.composeweatherapp.data.dto.currentweather.WindDto
import com.example.composeweatherapp.data.dto.forecast.ForecastDto
import com.example.composeweatherapp.data.dto.forecast.ForecastItemDto
import com.example.composeweatherapp.domain.entities.Forecast
import com.example.composeweatherapp.domain.entities.ForecastItem


fun CloudsDto.toClouds(): Clouds {
    return Clouds(
        all = all
    )

}

fun CurrentWeatherDto.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        base = this.base,
        name = this.name,
        weather = this.weather.map { it.toWeather() },
        id = this.id,
        main = this.main.toMain(),
        rain = this.rain?.toRain(),
        wind = this.wind?.toWind(),
        sys = this.sys?.toSys(),
        clouds = this.clouds?.toClouds(),
        timezone = this.timezone
    )
}

fun GeoCodingItemDto.toGeoCodingItem(): GeoCodingItem {
    return GeoCodingItem(
        country = this.country,
        lat = this.lat,
        lon = this.lon,
        name = this.name,
        state = this.state
    )
}

fun MainDto.toMain(): Main {
    return Main(
        feelsLike = this.feelsLike,
        seaLevel = this.seaLevel,
        groundLevel = this.groundLevel,
        temp = this.temp,
        tempMax = this.tempMax,
        tempMin = this.tempMin,
        humidity = this.humidity,
        pressure = this.pressure
    )
}

fun RainDto.toRain(): Rain {
    return Rain(
        oneHour = this.oneHour
    )
}

fun SysDto.toSys(): Sys {
    return Sys(
        country = this.country,
        id = this.id,
        sunrise = this.sunrise,
        sunset = this.sunset,
        type = this.type
    )
}

fun WeatherDto.toWeather(): Weather {
    return Weather(
        description = this.description,
        icon = this.icon,
        id = this.id,
        main = this.main
    )
}

fun WindDto.toWind(): Wind {
    return Wind(deg = this.deg, gust = this.gust, speed = this.speed)
}

fun ForecastItemDto.toForecastItem(): ForecastItem {
    return ForecastItem(
        clouds = this.clouds?.toClouds(),
        dt = this.dt,
        dtText = this.dtText,
        main = this.main.toMain(),
        pop = this.pop,
        rain = this.rain?.toRain(),
        sys = this.sys?.toSys(),
        visibility = this.visibility,
        weather = this.weather.map { it.toWeather() },
        wind = this.wind?.toWind()
    )
}

fun ForecastDto.toForecast(): Forecast {
    return Forecast(
        forecastItems = this.forecastItems.map { it.toForecastItem() },
        message = this.message
    )
}

