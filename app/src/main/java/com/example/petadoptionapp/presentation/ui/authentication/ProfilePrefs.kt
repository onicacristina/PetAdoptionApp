package com.example.petadoptionapp.presentation.ui.authentication

import com.example.petadoptionapp.network.models.EUserRole
import com.example.petadoptionapp.network.models.User
import com.example.petadoptionapp.presentation.ui.profile.settings.EAppTheme
import com.orhanobut.hawk.Hawk


private const val USER_PROFILE_KEY = "user_profile"
private const val APP_THEME = "app_theme"
private const val USER_ROLE = "user_role"

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

    fun setAppTheme(appTheme: EAppTheme) {
        Hawk.put(APP_THEME, appTheme)
    }

    fun getAppTheme(): EAppTheme {
        val defaultValue = EAppTheme.LIGHT
        return Hawk.get(APP_THEME, defaultValue)
    }

    fun saveUserRole(role: EUserRole) {
        Hawk.put(USER_ROLE, role)
    }

    fun getUserRole(): EUserRole {
        return Hawk.get(USER_ROLE, EUserRole.NORMAL_USER)
    }
}