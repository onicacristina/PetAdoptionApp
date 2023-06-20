package com.example.petadoptionapp.presentation.ui.authentication.login

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.EUserRole
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
    private val refreshTokenRepository: RefreshTokenRepository
) : BaseViewModel() {
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
            val userRole = ProfilePrefs().getUserRole()

            if (userRole == EUserRole.NORMAL_USER) {
                authRepository.login(loginParams = loginParams).fold(
                    onSuccess = {
                        ProfilePrefs().saveProfile(it.user)
                        Timber.e("user: ${it}")
                        refreshTokenRepository.saveRefreshToken(it.refreshToken)
                        Timber.e("refresh token: ${it.refreshToken}")
                        refreshTokenRepository.saveAccessToken(it.token)
                        Timber.e("success login")
                        _signedIn.send(Any())
                    },
                    onFailure = { error ->
                        Timber.e("error login ${error.message}")
                        showError(error)
                    })
            }
            if (userRole == EUserRole.ADOPTION_CENTER_USER) {
                authRepository.loginAdmin(loginParams = loginParams).fold(
                    onSuccess = {
                        ProfilePrefs().saveProfile(it.user)
                        Timber.e("user: $it")
                        refreshTokenRepository.saveRefreshToken(it.refreshToken)
                        refreshTokenRepository.saveAccessToken(it.token)
                        Timber.e("success login")
                        _signedIn.send(Any())
                    },
                    onFailure = { error ->
                        Timber.e("error login")
                        showError(error)
                    })
            }
        }
    }
}

