package com.example.petadoptionapp.presentation.ui.authentication

import com.example.petadoptionapp.network.models.User
import com.orhanobut.hawk.Hawk


private const val USER_PROFILE_KEY = "user_profile"
private const val APP_MODE = "app_mode"

class ProfilePrefs {

    fun saveProfile(userProfile: User) {
        Hawk.put(USER_PROFILE_KEY, userProfile)
    }

    fun clearUserProfile() {
        Hawk.delete(USER_PROFILE_KEY)
    }

    fun getProfile(): User? {
        return Hawk.get(USER_PROFILE_KEY)
    }

    fun isLoggedIn(): Boolean {
        return getProfile() != null
    }

    fun setAppMode(darkMode: Boolean) {
        Hawk.put(APP_MODE, darkMode)
    }

    fun getAppMode(): Boolean {
        return Hawk.get(APP_MODE, false)
    }
}