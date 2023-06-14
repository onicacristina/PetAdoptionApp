package com.example.petadoptionapp.network.models.response

data class NBookingResponse(
    val id: String,
    val userId: String,
    val adoptionCenterId: String,
    val dateAndTime: String,
)
