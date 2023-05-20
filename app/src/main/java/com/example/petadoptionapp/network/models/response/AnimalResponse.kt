package com.example.petadoptionapp.network.models.response

import com.example.petadoptionapp.presentation.ui.home.AgeCategory
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
    val adoptionCenterId: String,
) {
    fun getAgeCategory(animal: AnimalResponse): AgeCategory {
        return when (animal.specie) {
            EPetCategory.DOG -> {
                when {
                    animal.age <= 1 -> AgeCategory.PUPPY
                    animal.age <= 3 -> AgeCategory.YOUNG
                    animal.age <= 7 -> AgeCategory.ADULT
                    else -> AgeCategory.SENIOR
                }
            }
            EPetCategory.CAT -> {
                when {
                    animal.age <= 1 -> AgeCategory.PUPPY
                    animal.age <= 3 -> AgeCategory.YOUNG
                    animal.age <= 7 -> AgeCategory.ADULT
                    else -> AgeCategory.SENIOR
                }
            }
            EPetCategory.BIRD -> {
                when {
                    animal.age <= 1 -> AgeCategory.PUPPY
                    animal.age <= 3 -> AgeCategory.YOUNG
                    animal.age <= 10 -> AgeCategory.ADULT
                    else -> AgeCategory.SENIOR
                }
            }
            EPetCategory.RABBIT -> {
                when {
                    animal.age <= 1 -> AgeCategory.PUPPY
                    animal.age <= 3 -> AgeCategory.YOUNG
                    animal.age <= 6 -> AgeCategory.ADULT
                    else -> AgeCategory.SENIOR
                }
            }
            else -> throw IllegalArgumentException("Unknown category")
        }
    }

    companion object {
        val default = AnimalResponse(
            "",
            EPetCategory.ALL,
            EPetGender.FEMALE,
            "",
            "",
            0,
            false,
            false,
            "",
            "",
            ""
        )
    }

}