package com.example.petadoptionapp.network.services.auth

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.request.NChangePasswordParams
import com.example.petadoptionapp.network.models.request.NLinkAdminToAdoptionCenterParam
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.NMessageResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse
import javax.inject.Inject

class AuthApiInterfaceImplementation @Inject constructor(
    private val authApiService: AuthApiService
) : AuthApiInterface {
    override suspend fun login(loginParams: LoginParams): LoginResponse {
        return authApiService.login(loginParams = loginParams)
    }

    override suspend fun register(registerParams: RegisterParams): RegisterResponse {
        return authApiService.register(registerParams = registerParams)
    }

    override suspend fun changePassword(changePasswordParams: NChangePasswordParams): NMessageResponse {
        return authApiService.changePassword(changePasswordParams = changePasswordParams)
    }

    override suspend fun deleteAccount(): NMessageResponse {
        return authApiService.deleteAccount()
    }

    override suspend fun loginAdmin(loginParams: LoginParams): LoginResponse {
        return authApiService.loginAdmin(loginParams = loginParams)
    }

    override suspend fun registerAdmin(registerParams: RegisterParams): RegisterResponse {
        return authApiService.registerAdmin(registerParams = registerParams)
    }

    override suspend fun changePasswordAdmin(changePasswordParams: NChangePasswordParams): NMessageResponse {
        return authApiService.changePasswordAdmin(changePasswordParams = changePasswordParams)
    }

    override suspend fun linkAdminUserToAdoptionCenter(linkAdminToAdoptionCenterParam: NLinkAdminToAdoptionCenterParam): NMessageResponse {
        return authApiService.linkAdminUserToAdoptionCenter(linkAdminToAdoptionCenterParam)
    }

    override suspend fun deleteAccountAdmin(): NMessageResponse {
        return authApiService.deleteAccountAdmin()
    }
}