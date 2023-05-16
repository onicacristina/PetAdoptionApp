package com.example.petadoptionapp.presentation.ui.home

import com.example.petadoptionapp.R

enum class EPetGender (val iconResource: Int) {
    MALE(R.drawable.ic_male_gender_symbol),
    FEMALE(R.drawable.ic_female_gender_symbol);

    fun getPetGenderString(): String {
        return name.lowercase()
    }

    companion object {
        fun getPetGenderFromString(gender: String): EPetGender {
            return when (gender.lowercase()) {
                "male" -> MALE
                "female" -> FEMALE
                else -> throw IllegalArgumentException("Invalid pet gender: $gender")
            }
        }
    }
}
