package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.select_pet_gender.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.petadoptionapp.presentation.ui.home.EPetGender


object SelectPetGenderDiffUtils : DiffUtil.ItemCallback<EPetGender>() {
    override fun areItemsTheSame(
        oldItem: EPetGender, newItem: EPetGender
    ): Boolean {
        return oldItem.stringResource == newItem.stringResource
    }

    override fun areContentsTheSame(
        oldItem: EPetGender, newItem: EPetGender
    ): Boolean {
        return oldItem.iconResource == newItem.iconResource
    }
}