package com.example.petadoptionapp.presentation.utils

import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs

class LogoutUtils {

    fun logout() {
        ProfilePrefs().clearUserProfile()
    }
}