package com.example.petadoptionapp.presentation.ui.home

import com.example.petadoptionapp.R

enum class EPetCategory(val stringResource: Int? = null, val iconResource: Int? = null)  {
    ALL(R.string.all_pet_category),
    DOG(R.string.dog_pet_category, R.drawable.ic_dog_category),
    CAT(R.string.cat_pet_category, R.drawable.ic_cat_category),
    BIRD(R.string.bird_pet_category, R.drawable.ic_bird_category),
    RABBIT(R.string.rabbit_pet_category, R.drawable.ic_rabbit_category)
}