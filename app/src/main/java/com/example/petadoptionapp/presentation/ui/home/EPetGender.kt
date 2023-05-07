package com.example.petadoptionapp.presentation.ui.home

enum class EPetGender {
    MALE,
    FEMALE;

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
