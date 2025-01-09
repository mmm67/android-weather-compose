package com.example.composeweatherapp.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


fun extractTimeFromDateTimeString(dateTimeString: String): String {
    // 1. Parse the input string to LocalDateTime
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val dateTime = LocalDateTime.parse(dateTimeString, inputFormatter)

    // 2. Format the time to "HH:mm"
    val outputFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    return dateTime.format(outputFormatter)
}

fun isToday(dateTimeStr: String): Boolean {
    // Define the date-time format
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    // Parse the given string to a LocalDateTime
    val givenDateTime = LocalDateTime.parse(dateTimeStr, formatter)

    // Get the current date
    val currentDate = LocalDate.now()

    // Compare the date part of the given date-time with today's date
    return givenDateTime.toLocalDate() == currentDate
}

fun getDayOfWeekAbbreviation(date: LocalDate): String {
    // Get the day of the week
    val dayOfWeek = date.dayOfWeek

    // Return the abbreviated form (Mon, Tue, etc.)
    return dayOfWeek.name.take(3)  // e.g., "MONDAY" -> "Mon"
}

val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")