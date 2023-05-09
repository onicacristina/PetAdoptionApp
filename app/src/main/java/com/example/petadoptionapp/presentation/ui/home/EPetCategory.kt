package com.example.petadoptionapp.presentation.ui.home

import com.example.petadoptionapp.R

enum class EPetCategory(val stringResource: Int, val iconResource: Int) {
    ALL(R.string.all_pet_category, R.drawable.img_pets),
    DOG(R.string.dog_pet_category, R.drawable.img_dog2),
    CAT(R.string.cat_pet_category, R.drawable.img_cat),
    BIRD(R.string.bird_pet_category, R.drawable.img_bird),
    RABBIT(R.string.rabbit_pet_category, R.drawable.img_rabbit);

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

        fun getPetCategoryByIcon(iconResource: Int): EPetCategory {
            for (category in EPetCategory.values()) {
                if (category.iconResource == iconResource) {
                    return category
                }
            }
            return ALL
        }

    }
}