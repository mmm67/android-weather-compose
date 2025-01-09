package com.example.composeweatherapp.di

import com.example.composeweatherapp.data.repositories.WeatherRepositoryImpl
import com.example.composeweatherapp.domain.repositories.WeatherRepository
import com.example.composeweatherapp.remote.WeatherApi
import com.example.composeweatherapp.remote.WeatherDataSource
import com.example.composeweatherapp.utils.Constants.Network.BASE_URL
import com.example.coposeweatherapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideWeatherApi(okHttpClient: OkHttpClient): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherDataSource(weatherApi: WeatherApi, apiKey: String): WeatherDataSource {
        return WeatherDataSource(weatherApi, apiKey)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherDataSource: WeatherDataSource): WeatherRepository {
        return WeatherRepositoryImpl(weatherDataSource)
    }

    @Singleton
    @Provides
    fun provideApiKey(): String {
        return BuildConfig.apiKey
    }
}