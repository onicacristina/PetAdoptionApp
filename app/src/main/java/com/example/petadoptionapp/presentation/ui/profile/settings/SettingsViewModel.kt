package com.example.petadoptionapp.presentation.ui.profile.settings

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel(),
    EventDelegate<SettingsViewModel.Event> by DefaultEventDelegate() {

    fun deleteAccount() {
        viewModelScope.launch {
            val userRole = ProfilePrefs().getProfile()

            if (userRole?.role == 0) {
                authRepository.deleteAccount().fold(
                    onSuccess = {
                        Timber.e("success delete account")
                        sendEvent(Event.SUCCESS)
                    },
                    onFailure = { error ->
                        Timber.e("success delete account")
                        showError(error)
                    }
                )
            }

            if (userRole?.role == 1) {
                authRepository.deleteAccountAdmin().fold(
                    onSuccess = {
                        Timber.e("success delete account")
                        sendEvent(Event.SUCCESS)
                    },
                    onFailure = { error ->
                        Timber.e("success delete account")
                        showError(error)
                    }
                )
            }
        }
    }

    enum class Event {
        SUCCESS
    }
}