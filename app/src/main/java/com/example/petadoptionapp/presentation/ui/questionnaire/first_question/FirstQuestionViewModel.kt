package com.example.petadoptionapp.presentation.ui.questionnaire.first_question

import com.example.petadoptionapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FirstQuestionViewModel @Inject constructor() : BaseViewModel() {
    private val firstQuestionDetails: FirstQuestionDetails = FirstQuestionDetails.default
    var phoneNumber = ""
    var address = ""
    var city = ""

    private val _nextButtonState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val nextButtonState: Flow<Boolean>
        get() = _nextButtonState.asStateFlow()

    fun onPhoneNumberChanged(data: String) {
        phoneNumber = data
        firstQuestionDetails.phoneNumber = data
        validateInputsNotEmpty()
    }

    fun onCityChanged(data: String) {
        city = data
        firstQuestionDetails.city = data
        validateInputsNotEmpty()
    }

    fun onAddressChanged(data: String) {
        address = data
        firstQuestionDetails.address = data
        validateInputsNotEmpty()
    }

    fun onDateOfBirthChanged(data: Long) {
        firstQuestionDetails.dateOfBirth = data
        validateInputsNotEmpty()
    }

    private fun validateInputsNotEmpty() {
        _nextButtonState.value =
            phoneNumber.isNotEmpty() && address.isNotEmpty() && city.isNotEmpty() && firstQuestionDetails.dateOfBirth != 0L
    }
}