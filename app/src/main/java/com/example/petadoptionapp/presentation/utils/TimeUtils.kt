package com.example.petadoptionapp.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    fun convertTimestampToDateString(timestamp: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return formatter.format(timestamp)
    }
}