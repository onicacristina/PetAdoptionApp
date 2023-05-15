package com.example.petadoptionapp.presentation.ui.authentication.login

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.refresh_token.RefreshTokenRepository
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.authentication.InfoOrErrorAuthentication
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.utils.Constants
import com.example.petadoptionapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val refreshTokenRepository: RefreshTokenRepository) : BaseViewModel() {
    private var email = ""
    private var password = ""
    var isPasswordVisible = false

    private val _buttonState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val buttonState: Flow<Boolean>
        get() = _buttonState.asStateFlow()

    private val _signedIn: Channel<Any> = Channel()
    val signedIn: Flow<Any>
        get() = _signedIn.receiveAsFlow()

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

    fun loginUser() {
        viewModelScope.launch {
            val loginParams = LoginParams(email, password)
            authRepository.login(loginParams = loginParams).fold(
                onSuccess = {
                    Timber.e("success login")
                    ProfilePrefs().saveProfile(it.user)
                    it.refreshToken?.let { refreshToken ->
                        refreshTokenRepository.saveRefreshToken(refreshToken)
                    }
                    refreshTokenRepository.saveAccessToken(it.token)
                    _signedIn.send(Any())
                },
                onFailure = {
                    Timber.e("error login")
                })
        }
    }

}