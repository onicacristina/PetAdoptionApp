package com.example.petadoptionapp.presentation.ui.onboarding

import com.example.petadoptionapp.R

enum class EOnBoardingSliderType(val title: Int, val description: Int, val logo: Int) {
    PAGE_1(
        R.string.slide_1_title,
        R.string.slide_1_description,
        R.drawable.ic_animal_shelter_cuate
    ),
    PAGE_2(
        R.string.slide_2_title,
        R.string.slide_2_description,
        R.drawable.ic_animal_shelter_bro
    ),
    PAGE_3(
        R.string.slide_3_title,
        R.string.slide_3_description,
        R.drawable.ic_adopt_a_pet_amico
    ),
}