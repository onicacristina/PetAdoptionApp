package com.example.petadoptionapp.presentation.ui.profile.change_password

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.request.NChangePasswordParams
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import com.example.petadoptionapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel(),
    StateDelegate<ChangePasswordViewModel.State> by DefaultStateDelegate(State.default),
    EventDelegate<ChangePasswordViewModel.Event> by DefaultEventDelegate() {

    fun changePassword() {
        viewModelScope.launch {
            val password = currentState.password
            val newPassword = currentState.newPassword
            val confirmNewPassword = currentState.confirmNewPassword
            val params = NChangePasswordParams(
                oldPassword = password,
                newPassword = newPassword,
                confirmPassword = confirmNewPassword
            )

            if (!isCompletedCorrect(password, newPassword, confirmNewPassword)) {
                return@launch
            }

            val userRole = ProfilePrefs().getProfile()

            if (userRole?.role == 0) {
                val result = authRepository.changePassword(params)
                result.fold(
                    onSuccess = {
                        Timber.e("success change password")
                        sendEvent(Event.SUCCESS)
                    },
                    onFailure = { error ->
                        Timber.e("error change password")
                        showError(error)
                    }
                )
            }

            if (userRole?.role == 1) {
                val result = authRepository.changePasswordAdmin(params)
                result.fold(
                    onSuccess = {
                        Timber.e("success change password")
                        sendEvent(Event.SUCCESS)

                    },
                    onFailure = { error ->
                        Timber.e("error change password")
                        showError(error)
                    }
                )
            }
        }
    }

    private fun isCompletedCorrect(
        currentPassword: String,
        newPassword: String,
        confirmNewPassword: String
    ): Boolean {
        return when {
            currentPassword.length < 8 || newPassword.length < 8 || confirmNewPassword.length < 8 -> {
                sendEvent(Event.SHORT_PASSWORD)
                false
            }
            !isOneUpperCaseLetterInPassword() && !isOneNumberInPassword() -> {
                sendEvent(Event.PASSWORD_ONE_UPPERCASE_AND_ONE_NUMBER)
                false
            }
            newPassword != confirmNewPassword -> {
                sendEvent(Event.NO_MATCH_PASSWORD)
                false
            }
            else -> true
        }
    }

    private fun isOneNumberInPassword(): Boolean {
        return currentState.newPassword.any { it.isDigit() }
    }

    private fun isOneUpperCaseLetterInPassword(): Boolean {
        return currentState.newPassword.any { it.isUpperCase() }
    }

    private fun isNotEmptyPassword(): Boolean {
        return currentState.password.isNotEmpty()
    }

    private fun isNotEmptyNewPassword(): Boolean {
        return currentState.newPassword.isNotEmpty()
    }

    private fun isNotEmptyConfirmNewPassword(): Boolean {
        return currentState.confirmNewPassword.isNotEmpty()
    }

    private fun validateFieldsNotEmpty() {
        val isFieldsNotEmpty =
            isNotEmptyPassword() && isNotEmptyNewPassword() && isNotEmptyConfirmNewPassword()
        currentState = currentState.copy(isEnabledSaveButton = isFieldsNotEmpty)
    }

    fun onPasswordChanged(data: String) {
        currentState = currentState.copy(password = data)
        validateFieldsNotEmpty()
    }

    fun onNewPasswordChanged(data: String) {
        currentState = currentState.copy(newPassword = data)
        validateFieldsNotEmpty()
    }

    fun onConfirmNewPasswordChanged(data: String) {
        currentState = currentState.copy(confirmNewPassword = data)
        validateFieldsNotEmpty()
    }

    data class State(
        val password: String,
        val newPassword: String,
        val confirmNewPassword: String,
        val isEnabledSaveButton: Boolean = false
    ) {
        companion object {
            val default: State
                get() = State("", "", "")
        }
    }

    enum class Event {
        SUCCESS,
        SHORT_PASSWORD,
        NO_MATCH_PASSWORD,
        CHANGE_FAILURE,
        PASSWORD_ONE_UPPERCASE_AND_ONE_NUMBER
    }
}