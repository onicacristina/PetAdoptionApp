package com.example.petadoptionapp.network.models

import com.example.petadoptionapp.network.models.response.AnimalResponse

data class Booking(
    val id: String,
    val user: User,
    val adoptionCenter: AdoptionCenter,
    val animal: AnimalResponse,
    val dateAndTime: String,
)
