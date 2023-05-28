package com.example.petadoptionapp.presentation.ui.profile

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.User
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _userProfile: MutableStateFlow<User?> =
        MutableStateFlow(null)
    val userProfile: Flow<User?>
        get() = _userProfile

    init {
        getUser()
    }

    private fun getUser() {
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
                        Timber.e("user failure")
                        showError(error)
                    }
                )
            }
        }
    }
}