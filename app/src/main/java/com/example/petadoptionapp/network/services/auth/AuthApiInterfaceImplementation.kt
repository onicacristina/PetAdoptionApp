package com.example.petadoptionapp.network.services.auth

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse
import retrofit2.Call
import javax.inject.Inject

class AuthApiInterfaceImplementation @Inject constructor(
    private val authApiService: AuthApiService
) : AuthApiInterface{
    override suspend fun login(loginParams: LoginParams): Call<LoginResponse> {
        return authApiService.login(loginParams = loginParams)
    }

    override suspend fun register(registerParams: RegisterParams): Call<RegisterResponse> {
        return authApiService.register(registerParams = registerParams)
    }

}

//class UserApiInterfaceImplementation @Inject constructor(
//    private val userService: UserApiService
//) : UserApiInterface {
//
//    override suspend fun signIn(data: NSignInParams) = userService.signIn(data)
//    override suspend fun signOut(data: NDeviceIdParam) = userService.signOut(data)
//    override suspend fun registerDevice(data: NRegisterDeviceParams) = userService.registerDevice(data)
//    override suspend fun terminate() = userService.terminate()
//    override suspend fun getUser(id: String) = userService.getUser(id)
//    override fun editUser(id: String, data: NUserProfile) = userService.editUser(id, data)
//}