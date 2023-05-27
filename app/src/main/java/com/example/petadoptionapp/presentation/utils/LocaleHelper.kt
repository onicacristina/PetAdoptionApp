package com.example.petadoptionapp.presentation.utils

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.petadoptionapp.presentation.ui.language.ELanguage
import java.util.*

object LocaleHelper {

    fun setLocale(language: ELanguage) {
        val appLocale = LocaleListCompat.forLanguageTags(language.languageString)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    fun getLocale(): ELanguage {
        val locales = AppCompatDelegate.getApplicationLocales()
        val selectedLocale = locales[0] ?: Locale.US
        return ELanguage.values().firstOrNull { value -> value.locale.language == selectedLocale.language } ?: ELanguage.ENGLISH
    }

}