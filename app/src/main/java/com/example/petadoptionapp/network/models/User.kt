package com.example.petadoptionapp.network.models

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

data class User(
    val id: String = "",
    val role: Int = 0,
    val firstName: String? = null,
    val lastName: String? = null ,
    val phone: String? = null,
    val email: String? = null,
    val createdAt: String? = null
) {
    fun getFullName():String {
        return "$firstName $lastName"
    }

    fun getInitials(): String {
        val initials = StringBuilder()

        firstName?.let { initials.append(it.first().uppercase()) }
        lastName?.let { initials.append(".").append(it.first().uppercase()).append(".") }

        return initials.toString()
    }

    fun getFormattedCreationDate(locale: Locale = Locale.getDefault()): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale)
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", locale)

        val dateFormatSymbols = DateFormatSymbols.getInstance(locale)
        outputFormat.dateFormatSymbols = dateFormatSymbols

        return try {
            val date = inputFormat.parse(createdAt)
            outputFormat.format(date ?: "")
        } catch (e: Exception) {
            ""
        }
    }
}
