package com.example.petadoptionapp.network.models

import android.os.Parcelable
import com.example.petadoptionapp.network.models.response.AnimalResponse
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Booking(
    val id: String,
    val user: User,
    val adoptionCenter: AdoptionCenter,
    val animal: AnimalResponse,
    val dateAndTime: String,
) : Parcelable {
    fun getFormattedDate(): String {
        val dateFormatter = SimpleDateFormat("EEEE, MMMM yyyy, 'at' HH:mm", Locale.getDefault())
        val dateString =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(dateAndTime)
        return dateFormatter.format(dateString)
    }

    companion object {
        val default = Booking(
            id = "",
            user = User(),
            adoptionCenter = AdoptionCenter.default,
            animal = AnimalResponse.default,
            dateAndTime = ""
        )
    }
}
