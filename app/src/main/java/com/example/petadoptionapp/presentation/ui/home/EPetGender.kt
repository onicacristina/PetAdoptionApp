package com.example.petadoptionapp.presentation.ui.home

import com.example.petadoptionapp.R

enum class EPetGender(val iconResource: Int, val stringResource: Int) {
    NONE(0, 0),
    MALE(R.drawable.ic_male_gender_symbol, R.string.gender_male),
    FEMALE(R.drawable.ic_female_gender_symbol, R.string.gender_female);

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
