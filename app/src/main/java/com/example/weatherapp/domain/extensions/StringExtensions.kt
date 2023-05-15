package com.example.weatherapp.domain.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun String.toLocalDateTime(): LocalDateTime? = try {
    LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
} catch (e: DateTimeParseException) { null }

fun String.toLocalDate(): LocalDate? = try {
    LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
} catch (e: DateTimeParseException ) { null }