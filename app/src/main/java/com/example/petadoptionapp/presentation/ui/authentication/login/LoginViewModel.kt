package com.example.petadoptionapp.presentation.ui.authentication.login

import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.authentication.InfoOrErrorAuthentication
import com.example.petadoptionapp.presentation.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : BaseViewModel() {
    private var email = ""
    private var password = ""
    var isPasswordVisible = false

    private val _buttonState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val buttonState: Flow<Boolean>
        get() = _buttonState.asStateFlow()

    private fun isNotEmptyEmail(): Boolean {
        return email.isNotEmpty()
    }

    private fun isNotEmptyPassword(): Boolean {
        return password.isNotEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        val pattern = Regex(pattern = Constants.PATTERN)
        return pattern.matches(email)
    }

    fun onEmailChanged(data: String) {
        email = data
        validateFieldsNotEmpty()
    }

    fun onPasswordChanged(data: String) {
        password = data
        validateFieldsNotEmpty()
    }

    private fun validateFieldsNotEmpty() {
        _buttonState.value =
            isNotEmptyEmail() && isNotEmptyPassword()
    }

    fun getInputsErrors(): InfoOrErrorAuthentication {
        if (!isEmailValid(email))
            return InfoOrErrorAuthentication.EMAIL_INVALID
        return InfoOrErrorAuthentication.NONE
    }
}