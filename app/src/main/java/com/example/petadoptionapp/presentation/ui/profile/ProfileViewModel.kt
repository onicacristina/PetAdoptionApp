package com.example.petadoptionapp.presentation.ui.profile

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.ApiException
import com.example.petadoptionapp.network.models.User
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.utils.*
import com.example.petadoptionapp.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel(),
    StateDelegate<ProfileViewModel.State> by DefaultStateDelegate(State.Loading),
    EventDelegate<ProfileViewModel.Event> by DefaultEventDelegate() {

    private val _userProfile: MutableStateFlow<User?> =
        MutableStateFlow(null)
    val userProfile: Flow<User?>
        get() = _userProfile

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            val userId = ProfilePrefs().getProfile()?.id
            val response = userId?.let {
                userRepository.getUserById(it).fold(
                    onSuccess = { value ->
                        Timber.d("user response: $value")
                        _userProfile.value = value
                        //                    userRepository.saveUser(value)
                        //                    getOfflineUser()
                    },
                    onFailure = { error ->
                        Timber.e("user failure ${error.localizedMessage}")
                        Timber.e("user failure ${error.message}")
                        Timber.e("user failure ${error.cause?.message}")
                        showError(error)
                        if (error is ApiException){
                        Timber.e("error is api exception ${error.response?.globalErrors?.first()}")

                        }

                    }
                )
            }
        }
    }

    fun logOut() {
        LogoutUtils().logout()
        sendEvent(Event.SIGNED_OUT)
    }

    sealed class State {

        object Loading : State()
        object Empty : State()
//        data class Value(val licensePlates: List<LicensePlate>) : State()
    }

    enum class Event {
        SIGNED_OUT
    }
}