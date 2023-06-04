package com.example.petadoptionapp.presentation.ui.profile.edit_profile

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.User
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import com.example.petadoptionapp.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel(),
    StateDelegate<EditProfileViewModel.State> by DefaultStateDelegate(State.default),
    EventDelegate<EditProfileViewModel.Event> by DefaultEventDelegate() {

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            val userId = ProfilePrefs().getProfile()?.id
            val response = userId?.let {
                userRepository.getUserById(it).fold(
                    onSuccess = { user ->
                        currentState =
                            currentState.copy(
                                user = user,
                                firstName = user.firstName ?: "",
                                lastName = user.lastName ?: "",
                                phone = user.phone ?: ""
                            )
                    },
                    onFailure = { error ->
                        Timber.e("user failure")
                        showError(error)
                    }
                )
            }
        }
    }

    fun editUser() {
        viewModelScope.launch {
            val user = currentState.user
            val result = user?.id?.let {
                userRepository.editUser(it, user).fold(
                    onSuccess = {
                        sendEvent(Event.SUCCESS)
                    },
                    onFailure = { error ->
                        Timber.e("user failure")
                        showError(error)
                    }
                )
            }
        }
    }

    fun onFirstNameChanged(data: String) {
        currentState.user?.firstName = data
        currentState = currentState.copy(user = currentState.user)
        currentState = currentState.copy(firstName = data)
    }

    fun onLastNameChanged(data: String) {
        currentState.user?.lastName = data
        currentState = currentState.copy(user = currentState.user)
        currentState = currentState.copy(lastName = data)
    }

    fun onPhoneNumberChanged(data: String) {
        currentState.user?.phone = data
        currentState = currentState.copy(user = currentState.user)
        currentState = currentState.copy(phone = data)
    }

    data class State(
        val user: User?,
        val firstName: String,
        val lastName: String,
        val phone: String,
    ) {

        companion object {
            val default: State
                get() = State(null, "", "", "")
        }

    }

    enum class Event {
        SUCCESS
    }
}