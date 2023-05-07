package com.example.petadoptionapp.presentation.ui.home

import com.example.petadoptionapp.R

enum class EPetCategory(val stringResource: Int, val iconResource: Int) {
    ALL(R.string.all_pet_category, R.drawable.ic_all_category),
    DOG(R.string.dog_pet_category, R.drawable.ic_dog_category_icon),
    CAT(R.string.cat_pet_category, R.drawable.ic_cat_category_icon),
    BIRD(R.string.bird_pet_category, R.drawable.ic_bird_category),
    RABBIT(R.string.rabbit_pet_category, R.drawable.ic_rabbit_category);

    fun getPetCategoryString(): String {
        return name.lowercase()
    }

    companion object {
        fun getPetCategoryFromString(category: String): EPetCategory {
            for (element in values()) {
                if (element.name.lowercase() == category)
                    return element
            }
            return ALL
        }
    }
}