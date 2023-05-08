package com.example.petadoptionapp.network.models.response

import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.ui.home.EPetGender

data class AnimalResponse(
    val id: String,
    val specie: EPetCategory,
    val gender: EPetGender,
    val name: String,
    val breed: String,
    val age: Int,
    val vaccinated: Boolean,
    val neutered: Boolean,
    val story: String,
    val imageUrl: String,
)
