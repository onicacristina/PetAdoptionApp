package com.example.petadoptionapp.network.models.response

data class NAdoptionCenterResponse(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val city: String,
    val availableStart: String,
    val availableEnd: String,
)