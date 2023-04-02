package com.example.petadoptionapp.presentation.ui.authentication.register

import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.authentication.InfoOrErrorAuthentication
import com.example.petadoptionapp.presentation.utils.Constants.PATTERN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : BaseViewModel() {

    private var firstName = ""
    private var lastName = ""
    private var email = ""
    private var password = ""
    var isPasswordVisible = false
    private var isAcceptedTermsAndConditions = false

    private val _buttonState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val buttonState: Flow<Boolean>
        get() = _buttonState.asStateFlow()

    private fun isNotEmptyFirstName(): Boolean {
        return firstName.isNotEmpty()
    }

    private fun isNotEmptyLastName(): Boolean {
        return lastName.isNotEmpty()
    }

    private fun isNotEmptyEmail(): Boolean {
        return email.isNotEmpty()
    }

    private fun isNotEmptyPassword(): Boolean {
        return password.isNotEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        val pattern = Regex(pattern = PATTERN)
        return pattern.matches(email)
    }

    private fun isOneUppercaseLetterInPassword(): Boolean {
        return password.any { it.isUpperCase() }
    }

    private fun isOneNumberInPassword(): Boolean {
        return password.any { it.isDigit() }
    }

    private fun hasMin8Chars(): Boolean {
        return password.length >= 8
    }


    fun onFirstNameChanged(data: String) {
        firstName = data
        validateFieldsNotEmpty()
    }

    fun onLastNameChanged(data: String) {
        lastName = data
        validateFieldsNotEmpty()
    }

    fun onEmailChanged(data: String) {
        email = data
        validateFieldsNotEmpty()
    }

    fun onPasswordChanged(data: String) {
        password = data
        validateFieldsNotEmpty()
        validatePassword()
    }

    fun onTermsCheckedChanged(data: Boolean) {
        isAcceptedTermsAndConditions = data
        validateFieldsNotEmpty()
    }

//    fun sendSignInEvent(event: ValidationEventSignIn){
//        viewModelScope.launch {
//            _event.send(event)
//        }
//    }

    private fun validateFieldsNotEmpty() {
        _buttonState.value =
            isNotEmptyFirstName() && isNotEmptyLastName() && isNotEmptyEmail() && isNotEmptyPassword() && isAcceptedTermsAndConditions
    }

    private fun validatePassword(): Boolean {
        return isNotEmptyPassword() && isOneUppercaseLetterInPassword() && isOneNumberInPassword() && hasMin8Chars()
    }

    fun getInputsErrors(): InfoOrErrorAuthentication {
        if (!isEmailValid(email))
            return InfoOrErrorAuthentication.EMAIL_INVALID
        if (!hasMin8Chars())
            return InfoOrErrorAuthentication.PASSWORD_LENGTH
        if (!isOneNumberInPassword() && !isOneNumberInPassword())
            return InfoOrErrorAuthentication.PASSWORD_ONE_UPPERCASE_AND_ONE_NUMBER
        return InfoOrErrorAuthentication.NONE
    }

}