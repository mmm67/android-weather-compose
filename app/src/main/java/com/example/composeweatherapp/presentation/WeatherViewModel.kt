package com.example.composeweatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeweatherapp.domain.Result
import com.example.composeweatherapp.domain.entities.CurrentWeather
import com.example.composeweatherapp.domain.entities.DailyForecastItem
import com.example.composeweatherapp.domain.entities.ForecastItem
import com.example.composeweatherapp.domain.entities.ForecastResult
import com.example.composeweatherapp.domain.repositories.WeatherRepository
import com.example.composeweatherapp.utils.Constants.DEFAULT_CITY
import com.example.composeweatherapp.utils.dateFormatter
import com.example.composeweatherapp.utils.getDayOfWeekAbbreviation
import com.example.composeweatherapp.utils.isToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val _weatherUiState = MutableStateFlow(WeatherUiState())
    val weatherUiState = _weatherUiState
        .onStart {
            getCurrentWeather(DEFAULT_CITY)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            WeatherUiState()
        )


    private fun getCurrentWeather(city: String) {
        viewModelScope.launch {
            _weatherUiState.update {
                it.copy(
                    isLoading = true,
                    geoCodingError = null,
                    currentWeatherError = null,
                    forecastError = null
                )
            }

            when (val geoCodingResult = weatherRepository.getGeoCoding(city)) {
                is Result.Success -> {
                    if (geoCodingResult.data.isNotEmpty()) {
                        val geoCodingItem = geoCodingResult.data.first()
                        fetchCurrentWeather(
                            geoCodingItem.lat,
                            geoCodingItem.lon
                        )
                    } else {
                        _weatherUiState.update { previousState ->
                            previousState.copy(
                                geoCodingResult = null,
                                geoCodingError = null,
                                forecastResult = null,
                                forecastError = null,
                                isLoading = false

                            )
                        }
                    }
                }

                is Result.Error -> {
                    _weatherUiState.update { previousState ->
                        previousState.copy(
                            geoCodingResult = null,
                            geoCodingError = geoCodingResult.error,
                            forecastResult = null,
                            forecastError = null,
                            isLoading = false
                        )
                    }
                }
            }
        }

    }


    private fun fetchCurrentWeather(
        lat: Double,
        lon: Double,
        units: String = "metric"
    ) {
        viewModelScope.launch {
            when (val weatherResult = weatherRepository.getCurrentWeather(lat, lon, units)) {
                is Result.Success -> {
                    fetchForecast(lat, lon, units, weatherResult.data)
                }

                is Result.Error -> {
                    _weatherUiState.update { previousState ->
                        previousState.copy(
                            currentWeatherResult = null,
                            currentWeatherError = weatherResult.error,
                            forecastResult = null,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun getDailyForecastItem(forecastItems: List<ForecastItem>): List<DailyForecastItem> {
        return forecastItems.filter { forecastItem ->
            LocalDate.parse(forecastItem.dtText, dateFormatter)
                .isAfter(LocalDate.now())
        }.groupBy {
            LocalDate.parse(it.dtText, dateFormatter)
        }.map { (date, items) ->
            DailyForecastItem(
                day = getDayOfWeekAbbreviation(date),
                maxTemp = items.maxOf { it.main.tempMax ?: 0.0 },
                minTemp = items.minOf { it.main.tempMin ?: 0.0 }
            )
        }
    }

    private fun fetchForecast(
        lat: Double,
        lon: Double,
        units: String = "metric",
        currentWeatherResult: CurrentWeather
    ) {
        viewModelScope.launch {
            when (val forecastResult = weatherRepository.getForecast(lat, lon, units)) {
                is Result.Success -> {
                    _weatherUiState.update { previousState ->
                        previousState.copy(
                            currentWeatherResult = currentWeatherResult,
                            currentWeatherError = null,
                            forecastError = null,
                            forecastResult = previousState.forecastResult?.let { result ->
                                result.copy(
                                    todayForecast = forecastResult.data.forecastItems.filter {
                                        isToday(it.dtText)
                                    },
                                    daysForecast = getDailyForecastItem(forecastResult.data.forecastItems)
                                )
                            } ?: ForecastResult(
                                todayForecast = forecastResult.data.forecastItems.filter {
                                    isToday(it.dtText)
                                },
                                daysForecast = getDailyForecastItem(forecastResult.data.forecastItems)
                            ),
                            isLoading = false
                        )
                    }
                }

                is Result.Error -> {
                    _weatherUiState.update { previousState ->
                        previousState.copy(
                            currentWeatherResult = null,
                            currentWeatherError = null,
                            forecastError = forecastResult.error,
                            forecastResult = null,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.GetCurrentWeather -> {
                getCurrentWeather(event.city)
            }
        }
    }
}