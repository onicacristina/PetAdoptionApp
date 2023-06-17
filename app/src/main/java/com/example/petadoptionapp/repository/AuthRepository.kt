package com.example.petadoptionapp.repository

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse
import com.example.petadoptionapp.network.services.auth.AuthApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authApiInterface: AuthApiInterface
) : AuthRepositoryInterface {
    override suspend fun login(loginParams: LoginParams): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                authApiInterface.login(loginParams = loginParams)
            }
        }
    }

    override suspend fun register(registerParams: RegisterParams): Result<RegisterResponse> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                authApiInterface.register(registerParams = registerParams)
            }
        }
    }

    override suspend fun registerAdmin(registerParams: RegisterParams): Result<RegisterResponse> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                authApiInterface.registerAdmin(registerParams = registerParams)
            }
        }
    }

    override suspend fun loginAdmin(loginParams: LoginParams): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                authApiInterface.loginAdmin(loginParams = loginParams)
            }
        }
    }

}