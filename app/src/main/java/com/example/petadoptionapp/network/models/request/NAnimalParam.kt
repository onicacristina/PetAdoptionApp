package com.example.petadoptionapp.network.models.request

import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.ui.home.EPetGender

data class NAnimalParam(
    val name: String,
    val specie: EPetCategory,
    val gender: EPetGender,
    val breed: String,
    val age: Int,
    val vaccinated: Boolean,
    val neutered: Boolean,
    val story: String,
    val imageUrl: String,
    val adoptionCenterId: String,
)
