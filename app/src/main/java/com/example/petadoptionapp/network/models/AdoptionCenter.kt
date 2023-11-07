package com.example.petadoptionapp.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdoptionCenter(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val city: String,
    val availableStart: String,
    val availableEnd: String,
) : Parcelable {
    fun getFullAddress(): String {
        return "$address, $city"
    }

    companion object {
        val default = AdoptionCenter("", "", "", "", "", "", "", "")
    }
}
