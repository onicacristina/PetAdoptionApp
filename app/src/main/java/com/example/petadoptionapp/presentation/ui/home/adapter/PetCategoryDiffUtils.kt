package com.example.petadoptionapp.presentation.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.petadoptionapp.presentation.ui.home.PetCategoryModel

class PetCategoryDiffUtils : DiffUtil.ItemCallback<PetCategoryModel>(){
    override fun areItemsTheSame(oldItem: PetCategoryModel, newItem: PetCategoryModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PetCategoryModel, newItem: PetCategoryModel): Boolean {
        return oldItem.isSelected == newItem.isSelected
    }
}