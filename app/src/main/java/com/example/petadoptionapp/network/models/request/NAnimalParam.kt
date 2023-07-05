package com.example.petadoptionapp.network.models.request

data class NAnimalParam(
    val name: String,
    val specie: String,
    val gender: String,
    val breed: String,
    val age: Int,
    val vaccinated: Boolean,
    val neutered: Boolean,
    val story: String,
//    val imageUrl: String?,
    val extraData: Map<String, Any>,
    val adoptionCenterId: String,
)
