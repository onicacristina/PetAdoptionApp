package com.example.petadoptionapp.network.models.request

data class NBookingParams(
    val userId: String,
    val adoptionCenterId: String,
    val animalId: String,
    val timestamp: String
)
