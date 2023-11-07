package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.select_pet_specie.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.petadoptionapp.presentation.ui.home.EPetCategory

object SelectPetSpecieDiffUtils : DiffUtil.ItemCallback<EPetCategory>() {
    override fun areItemsTheSame(
        oldItem: EPetCategory, newItem: EPetCategory
    ): Boolean {
        return oldItem.stringResource == newItem.stringResource
    }

    override fun areContentsTheSame(
        oldItem: EPetCategory, newItem: EPetCategory
    ): Boolean {
        return oldItem.iconResource == newItem.iconResource
    }
}