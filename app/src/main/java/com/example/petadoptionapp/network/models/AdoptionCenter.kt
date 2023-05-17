package com.example.petadoptionapp.network.models

data class AdoptionCenter(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val city: String,
    val availableStart: String,
    val availableEnd: String,
) {
    fun getFullAddress(): String {
        return "$address, $city"
    }

    companion object {
        val default = AdoptionCenter( "", "", "", "", "", "", "", "")
    }
}
