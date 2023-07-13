package com.example.petadoptionapp.network.models.response

import com.example.petadoptionapp.network.models.AdoptionCenter
import com.example.petadoptionapp.network.models.User

data class NBookingResponse(
    val id: String,
    val user: User,
    val adoptionCenter: AdoptionCenter,
    val animal: AnimalResponse,
    val dateAndTime: String,
)
