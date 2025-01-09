package com.example.composeweatherapp.presentation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.coposeweatherapp.R
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.composeweatherapp.domain.entities.CurrentWeather
import com.example.composeweatherapp.domain.entities.DailyForecastItem
import com.example.composeweatherapp.domain.entities.ForecastItem
import com.example.composeweatherapp.utils.Constants.DEFAULT_CITY
import com.example.composeweatherapp.utils.extractTimeFromDateTimeString
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherUiState: WeatherUiState,
    onEvent: (WeatherEvent) -> Unit
) {
    var city by remember { mutableStateOf("") }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = weatherUiState.isLoading)

    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = Color.Transparent,
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = {
                    Text(
                        "Enter city name",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { onEvent(WeatherEvent.GetCurrentWeather(city)) }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "search icon",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    onEvent(
                        WeatherEvent.GetCurrentWeather(
                            weatherUiState.currentWeatherResult?.name ?: DEFAULT_CITY
                        )
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (weatherUiState.currentWeatherResult != null) {
                        LocationHeader(cityName = weatherUiState.currentWeatherResult.name)
                        CurrentWeatherCard(weather = weatherUiState.currentWeatherResult)
                        Spacer(modifier = Modifier.height(24.dp))
                        weatherUiState.forecastResult?.todayForecast?.let { TodayForecastSection(it) }
                        Spacer(modifier = Modifier.height(24.dp))
                        weatherUiState.forecastResult?.daysForecast?.let { DaysForecastSection(it) }
                    }
                }
            }
        }
    }
}

@Composable
fun LocationHeader(cityName: String) {
    Text(
        modifier = Modifier.padding(top = 48.dp),
        text = cityName,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.headlineLarge,
    )

    Spacer(modifier = Modifier.height(8.dp))

    val currentDate = LocalDate.now()
    val formattedDate = DateTimeFormatter.ofPattern("MMM d, yyyy").format(currentDate)

    Text(
        text = formattedDate,
        color = MaterialTheme.colorScheme.onSurface,
    )
}

@Composable
fun CurrentWeatherCard(weather: CurrentWeather) {
    val currentWeather = weather.weather.first()
    Card(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    painter = painterResource(id = mapIconCodeToDrawable(currentWeather.icon)),
                    contentDescription = currentWeather.description,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(top = 20.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        modifier = Modifier.padding(top = 24.dp),
                        text = weather.main.temp.toString() + "°",
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        modifier = Modifier.padding(top = 24.dp),
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Feels like:")
                            }
                            append(" ${weather.main.feelsLike}°")
                        },
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    val highTemp =
                        (weather.main.tempMax ?: weather.main.temp).toBigDecimal()
                            .setScale(2, RoundingMode.HALF_UP)
                            .toDouble()
                    val lowTemp =
                        (weather.main.tempMin ?: weather.main.temp).toBigDecimal()
                            .setScale(2, RoundingMode.HALF_UP)
                            .toDouble()
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("High:")
                            }
                            append(" $highTemp°")
                        },
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Low:")
                            }
                            append(" $lowTemp°")
                        },
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            WeatherDetailsRow(
                windSpeed = "${weather.wind?.speed}",
                humidity = "${weather.main.humidity}%"
            )
        }
    }
}


@Composable
fun WeatherDetailsRow(
    windSpeed: String,
    humidity: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DetailsItem(icon = R.drawable.wind, text = windSpeed, label = "Wind speed")
        DetailsItem(icon = R.drawable.drop, text = humidity, label = "Humidity")
    }
}

@Composable
fun DetailsItem(icon: Int, text: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp
        )
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp
        )
    }
}

@Composable
fun TodayForecastSection(
    todayForecast: List<ForecastItem>,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        text = "Today",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(todayForecast) { forecastItem ->
            HourlyForecastItem(forecastItem)
        }
    }
}

@Composable
fun HourlyForecastItem(forecastItem: ForecastItem) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = extractTimeFromDateTimeString(forecastItem.dtText),
            color = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            painter = painterResource(id = mapIconCodeToDrawable(forecastItem.weather.first().icon)),
            contentDescription = forecastItem.weather.first().description,
            modifier = Modifier.size(32.dp),
            tint = Color.Unspecified
        )
        Text(
            text = forecastItem.main.temp.toString() + "°",
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun DaysForecastSection(
    forecastItems: List<DailyForecastItem>
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Next 5 Days:",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )

    LazyRow(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(4.dp)) {
        items(forecastItems) { item ->
            DailyForecastItemScreen(item)
        }

    }
}

@Composable
fun DailyForecastItemScreen(
    dailyForecastItem: DailyForecastItem
) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dailyForecastItem.day,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.high_temperature),
                    contentDescription = "Max Temperature Icon",
                    modifier = Modifier.size(25.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = "${dailyForecastItem.maxTemp}°",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.low_temperature),
                    contentDescription = "Min Temperature Icon",
                    modifier = Modifier.size(25.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = "${dailyForecastItem.minTemp}°",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

fun mapIconCodeToDrawable(iconCode: String): Int {
    return when (iconCode) {
        "01d", "01n" -> R.drawable.sunny
        "02d", "03d" -> R.drawable.partly_cloudy
        "02n", "03n" -> R.drawable.partly_cloudy_night
        "04d" -> R.drawable.broken_cloud
        "04n" -> R.drawable.brokencloudnight
        "09d", "09n" -> R.drawable.shower_rainy_day
        "10d" -> R.drawable.rainy_day
        "10n" -> R.drawable.rainy_night
        "11d", "11n" -> R.drawable.thunder
        "13d", "13n" -> R.drawable.snowy
        "50d", "50n" -> R.drawable.mist
        else -> R.drawable.sunny
    }
}