package com.example.petadoptionapp.network.models.response

data class AnimalResponse(
    val id: String,
    val specie: String,
    val gender: String,
    val name: String,
    val breed: String,
    val age: Int,
    val vaccinated: Boolean,
    val neutered: Boolean,
    val story: String,
    val imageUrl: String,
)
