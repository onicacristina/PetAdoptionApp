package com.example.petadoptionapp.presentation.ui.profile.settings.language_settings.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.petadoptionapp.presentation.ui.profile.settings.language_settings.Language

object LanguageDiffUtils : DiffUtil.ItemCallback<Language>() {
    override fun areItemsTheSame(
        oldItem: Language, newItem: Language
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Language, newItem: Language
    ): Boolean {
        return oldItem.isSelected == newItem.isSelected
    }

    override fun getChangePayload(
        oldItem: Language,
        newItem: Language
    ): Any? {
        return super.getChangePayload(oldItem, newItem)
    }
}