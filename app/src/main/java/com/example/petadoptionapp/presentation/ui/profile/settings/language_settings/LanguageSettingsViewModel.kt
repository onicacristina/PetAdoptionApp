package com.example.petadoptionapp.presentation.ui.profile.settings.language_settings

import com.example.petadoptionapp.R
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.language.ELanguage
import com.example.petadoptionapp.presentation.utils.LocaleHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LanguageSettingsViewModel @Inject constructor() : BaseViewModel() {

    private val _languageObservable: MutableStateFlow<List<Language>> =
        MutableStateFlow(mutableListOf())
    val languageObservable: Flow<List<Language>>
        get() = _languageObservable

    var languageSelected: ELanguage = LocaleHelper.getLocale()


    init {
        getDummyData()
    }

    private fun getDummyData() {
        _languageObservable.value = listOf(
            Language(
                1L,
                ELanguage.ENGLISH,
                R.string.english_language,
                isSelected = ELanguage.ENGLISH == languageSelected
            ),
            Language(
                2L,
                ELanguage.ROMANIAN,
                R.string.romanian_language,
                isSelected = ELanguage.ROMANIAN == languageSelected
            ),
        )
    }

    fun selectLanguage(data: Language) {
        val oldData = _languageObservable.value
        val newData = oldData.map { value ->
            val isSelected = value.id == data.id
            value.copy(isSelected = isSelected)
        }
        _languageObservable.value = newData
        languageSelected = data.languageString
    }
}