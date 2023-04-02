package com.example.petadoptionapp.presentation.ui.authentication.register

import com.example.petadoptionapp.R

enum class EPasswordState(val colorOne: Int, val colorTwo: Int) {
    CORRECT(R.color.blue_light, R.color.blue_light),
    INCORRECT(R.color.text_grey, R.color.text_grey),
    ONE_UPPERCASE(R.color.text_grey, R.color.blue_light),
    ONE_NUMBER(R.color.blue_light, R.color.text_grey)

}