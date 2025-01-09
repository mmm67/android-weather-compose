package com.example.composeweatherapp.data.repositories

import com.example.composeweatherapp.domain.DataError
import com.example.composeweatherapp.domain.Result
import com.example.composeweatherapp.domain.repositories.WeatherRepository
import com.example.composeweatherapp.domain.entities.CurrentWeather
import com.example.composeweatherapp.domain.entities.Forecast
import com.example.composeweatherapp.domain.entities.GeoCodingItem
import com.example.composeweatherapp.mappers.toCurrentWeather
import com.example.composeweatherapp.mappers.toForecast
import com.example.composeweatherapp.mappers.toGeoCodingItem
import com.example.composeweatherapp.remote.WeatherDataSource
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherDataSource: WeatherDataSource) :
    WeatherRepository {
    override suspend fun getGeoCoding(
        city: String,
        limit: Int
    ): Result<List<GeoCodingItem>, DataError.Network> {
        return try {
            val geoCodingItemList = weatherDataSource.getGeoCoding(city, limit)
            Result.Success(geoCodingItemList.map { it.toGeoCodingItem() })
        } catch (e: SocketTimeoutException) {
            Result.Error(DataError.Network.REQUEST_TIMEOUT)
        } catch (e: UnknownHostException) {
            Result.Error(DataError.Network.NO_INTERNET)
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> Result.Error(DataError.Network.UNAUTHORIZED)
                429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
                413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
                in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        } catch (e: SerializationException) {
            Result.Error(DataError.Network.SERIALIZATION)
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        units: String
    ): Result<CurrentWeather, DataError.Network> {
        return try {
            val currentWeather = weatherDataSource.getCurrentWeather(lat, lon, units)
            Result.Success(currentWeather.toCurrentWeather())
        } catch (e: SocketTimeoutException) {
            Result.Error(DataError.Network.REQUEST_TIMEOUT)
        } catch (e: UnknownHostException) {
            Result.Error(DataError.Network.NO_INTERNET)
        } catch (e: HttpException) {
            when (e.code()) {
                429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
                413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
                in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        } catch (e: SerializationException) {
            Result.Error(DataError.Network.SERIALIZATION)
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    override suspend fun getForecast(
        lat: Double,
        lon: Double,
        units: String
    ): Result<Forecast, DataError> {
        return try {
            val forecast = weatherDataSource.getForecast(lat, lon, units)
            Result.Success(forecast.toForecast())
        } catch (e: SocketTimeoutException) {
            Result.Error(DataError.Network.REQUEST_TIMEOUT)
        } catch (e: UnknownHostException) {
            Result.Error(DataError.Network.NO_INTERNET)
        } catch (e: HttpException) {
            when (e.code()) {
                429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
                413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
                in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        } catch (e: SerializationException) {
            Result.Error(DataError.Network.SERIALIZATION)
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }
}