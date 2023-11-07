package com.example.petadoptionapp.presentation.ui.home

import com.example.petadoptionapp.R

enum class AgeCategory(private val dogDisplayName: Int, private val catDisplayName: Int, private val birdDisplayName: Int, private val rabbitDisplayName: Int) {
    PUPPY(R.string.pet_puppy, R.string.pet_kitten, R.string.pet_chick, R.string.pet_bunny),
    YOUNG(R.string.pet_young, R.string.pet_young, R.string.pet_young, R.string.pet_young),
    ADULT(R.string.pet_adult, R.string.pet_adult, R.string.pet_adult, R.string.pet_adult),
    SENIOR(R.string.pet_senior, R.string.pet_senior, R.string.pet_senior, R.string.pet_senior);

    fun getDisplayName(specie: EPetCategory): Int {
        return when (specie) {
            EPetCategory.DOG -> dogDisplayName
            EPetCategory.CAT -> catDisplayName
            EPetCategory.BIRD -> birdDisplayName
            EPetCategory.RABBIT -> rabbitDisplayName
            else -> throw IllegalArgumentException("Unknown specie")
        }
    }
}

