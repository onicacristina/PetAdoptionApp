package com.example.petadoptionapp.network.services.auth

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse
import retrofit2.Call

interface AuthApiInterface {

    suspend fun login(loginParams: LoginParams): Call<LoginResponse>
    suspend fun register(registerParams: RegisterParams): Call<RegisterResponse>
}
//interface UserApiInterface {
//
//    suspend fun signIn(data: NSignInParams) : Call<NSignInResponse>
//    suspend fun signOut(data: NDeviceIdParam) : Call<NMessageResponse>
//    suspend fun registerDevice(data: NRegisterDeviceParams) : Call<NMessageResponse>
//    suspend fun terminate() : Call<NMessageResponse>
//    suspend fun getUser(id: String) : Call<NUserProfile>
//    fun editUser(id: String, data: NUserProfile) : Call<NMessageResponse>
//}