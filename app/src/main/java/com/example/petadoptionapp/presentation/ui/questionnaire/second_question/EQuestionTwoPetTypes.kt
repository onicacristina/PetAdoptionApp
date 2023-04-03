package com.example.petadoptionapp.presentation.ui.questionnaire.second_question

import com.example.petadoptionapp.R

enum class EQuestionTwoPetTypes(val type: Int, val iconResource: Int) {
    CAT(R.string.cat, R.drawable.ic_cat_type),
    DOG(R.string.dog, R.drawable.ic_dog_type),
    OTHER(R.string.other, R.drawable.ic_other_pet)
}