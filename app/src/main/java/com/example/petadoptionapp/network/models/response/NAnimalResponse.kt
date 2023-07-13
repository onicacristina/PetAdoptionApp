package com.example.petadoptionapp.network.models.response

import com.example.petadoptionapp.network.models.UploadedAssets

data class NAnimalResponse(
    val id: String,
    val specie: String,
    val gender: String,
    val name: String,
    val breed: String,
    val age: Int,
    val vaccinated: Boolean,
    val neutered: Boolean,
    val story: String,
//    val imageUrl: String,
    val adoptionCenterId: String,
    val uploadedAssets: List<UploadedAssets>,
    val createdAt: String,
    val updatedAt: String,
)