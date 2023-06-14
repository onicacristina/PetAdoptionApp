package com.example.petadoptionapp.network.models.response

import android.os.Parcelable
import com.example.petadoptionapp.presentation.ui.home.AgeCategory
import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class AnimalResponse(
    val id: String,
    val specie: EPetCategory,
    val gender: EPetGender,
    val name: String,
    val breed: String,
    val age: Int,
    val vaccinated: Boolean,
    val neutered: Boolean,
    val story: String,
    val imageUrl: String,
    val adoptionCenterId: String,
    val isSaved: Boolean = false
) : Parcelable {
    fun getAgeCategory(animal: AnimalResponse): AgeCategory {
        return when (animal.specie) {
            EPetCategory.DOG -> {
                when {
                    animal.age <= 1 -> AgeCategory.PUPPY
                    animal.age <= 3 -> AgeCategory.YOUNG
                    animal.age <= 7 -> AgeCategory.ADULT
                    else -> AgeCategory.SENIOR
                }
            }
            EPetCategory.CAT -> {
                when {
                    animal.age <= 1 -> AgeCategory.PUPPY
                    animal.age <= 3 -> AgeCategory.YOUNG
                    animal.age <= 7 -> AgeCategory.ADULT
                    else -> AgeCategory.SENIOR
                }
            }
            EPetCategory.BIRD -> {
                when {
                    animal.age <= 1 -> AgeCategory.PUPPY
                    animal.age <= 3 -> AgeCategory.YOUNG
                    animal.age <= 10 -> AgeCategory.ADULT
                    else -> AgeCategory.SENIOR
                }
            }
            EPetCategory.RABBIT -> {
                when {
                    animal.age <= 1 -> AgeCategory.PUPPY
                    animal.age <= 3 -> AgeCategory.YOUNG
                    animal.age <= 6 -> AgeCategory.ADULT
                    else -> AgeCategory.SENIOR
                }
            }
            else -> throw IllegalArgumentException("Unknown category")
        }
    }

    companion object {
        val default = AnimalResponse(
            "",
            EPetCategory.ALL,
            EPetGender.FEMALE,
            "",
            "",
            0,
            false,
            false,
            "",
            "",
            ""
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnimalResponse

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}