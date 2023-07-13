package com.example.petadoptionapp.repository

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.request.NChangePasswordParams
import com.example.petadoptionapp.network.models.request.NLinkAdminToAdoptionCenterParam
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.NMessageResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse


interface AuthRepositoryInterface {

    suspend fun register(registerParams: RegisterParams): Result<RegisterResponse>
    suspend fun login(loginParams: LoginParams): Result<LoginResponse>
    suspend fun changePassword(changePasswordParams: NChangePasswordParams): Result<NMessageResponse>
    suspend fun deleteAccount(): Result<NMessageResponse>



    suspend fun registerAdmin(registerParams: RegisterParams): Result<RegisterResponse>
    suspend fun loginAdmin(loginParams: LoginParams): Result<LoginResponse>
    suspend fun changePasswordAdmin(changePasswordParams: NChangePasswordParams): Result<NMessageResponse>
    suspend fun linkAdminUserToAdoptionCenter(linkAdminToAdoptionCenterParam: NLinkAdminToAdoptionCenterParam): Result<NMessageResponse>
    suspend fun deleteAccountAdmin(): Result<NMessageResponse>
}