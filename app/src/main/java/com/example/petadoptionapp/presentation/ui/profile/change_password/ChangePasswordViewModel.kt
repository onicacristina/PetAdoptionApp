package com.example.petadoptionapp.presentation.ui.profile.change_password

import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(

) : BaseViewModel(),
    StateDelegate<ChangePasswordViewModel.State> by DefaultStateDelegate(State.default),
    EventDelegate<ChangePasswordViewModel.Event> by DefaultEventDelegate() {

    fun onPasswordChanged(data: String) {
        currentState = currentState.copy(password = data)
    }

    fun onNewPasswordChanged(data: String) {
        currentState = currentState.copy(newPassword = data)
    }

    fun onConfirmNewPasswordChanged(data: String) {
        currentState = currentState.copy(confirmNewPassword = data)
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
        CHANGE_FAILURE
    }
}