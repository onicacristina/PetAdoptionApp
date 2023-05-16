package com.example.petadoptionapp.network.refresh_token

import com.orhanobut.hawk.Hawk
import javax.inject.Inject

private const val ACCESS_TOKEN = "ACCESS_TOKEN"
private const val REFRESH_TOKEN = "REFRESH_TOKEN"

class RefreshTokenRepository @Inject constructor() {

    fun getAccessToken(): String? = Hawk.get(ACCESS_TOKEN)

    fun saveAccessToken(token: String) {
        Hawk.put(ACCESS_TOKEN, token)
    }

    fun getRefreshToken(): String? = Hawk.get(REFRESH_TOKEN)

    fun saveRefreshToken(refreshToken: String) {
        Hawk.put(REFRESH_TOKEN, refreshToken)
    }
}