package com.example.petadoptionapp.presentation.ui.profile.settings.language_settings

import com.example.petadoptionapp.presentation.ui.language.ELanguage

data class Language(
    val id: Long,
    val languageString: ELanguage,
    val language: Int,
    val isSelected: Boolean = false
)
