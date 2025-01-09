package com.example.composeweatherapp.domain

sealed interface DataError: Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNAUTHORIZED,
        UNKNOWN
    }
}