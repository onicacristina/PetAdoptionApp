package com.example.petadoptionapp.network.services.auth

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.request.NChangePasswordParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.NMessageResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse

interface AuthApiInterface {

    suspend fun login(loginParams: LoginParams): LoginResponse
    suspend fun register(registerParams: RegisterParams): RegisterResponse
    suspend fun changePassword(changePasswordParams: NChangePasswordParams): NMessageResponse
    suspend fun deleteAccount(): NMessageResponse


    suspend fun loginAdmin(loginParams: LoginParams): LoginResponse
    suspend fun registerAdmin(registerParams: RegisterParams): RegisterResponse
    suspend fun changePasswordAdmin(changePasswordParams: NChangePasswordParams): NMessageResponse
    suspend fun linkAdminUserToAdoptionCenter(adoptionCenterId: String): NMessageResponse
    suspend fun deleteAccountAdmin(): NMessageResponse

}
