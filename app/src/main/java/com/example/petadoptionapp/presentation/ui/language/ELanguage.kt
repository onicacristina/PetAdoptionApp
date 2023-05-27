package com.example.petadoptionapp.presentation.ui.language

import java.util.*

enum class ELanguage(val languageString: String) {
    NONE(""),
    ROMANIAN("ro"),
    ENGLISH("en");

    val locale: Locale
        get() = Locale.forLanguageTag(languageString)
}