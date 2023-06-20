package com.example.petadoptionapp.network.services.auth

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.request.NChangePasswordParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.NMessageResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse
import com.example.petadoptionapp.presentation.utils.Constants
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApiService {

    //user (normal)
    @POST("api/auth/login")
    suspend fun login(@Body loginParams: LoginParams): LoginResponse

    @POST("api/auth/register")
    suspend fun register(@Body registerParams: RegisterParams): RegisterResponse

    @PUT("api/auth/change-password")
    suspend fun changePassword(@Body changePasswordParams: NChangePasswordParams): NMessageResponse

    @DELETE("api/auth/terminate")
    suspend fun deleteAccount(): NMessageResponse


    //admin - adoption center
    @POST("admin/auth/login")
    suspend fun loginAdmin(
        @Header("Authorization") authorization: String = Constants.NO_AUTH,
        @Body loginParams: LoginParams
    ): LoginResponse

    @POST("admin/auth/register")
    suspend fun registerAdmin(
        @Header("Authorization") authorization: String = Constants.NO_AUTH,
        @Body registerParams: RegisterParams
    ): RegisterResponse

    @PUT("admin/auth/change-password")
    suspend fun changePasswordAdmin(@Body changePasswordParams: NChangePasswordParams): NMessageResponse

    @PUT("admin/users/link-to-adoption-center")
    suspend fun linkAdminUserToAdoptionCenter(@Body adoptionCenterId: String) : NMessageResponse // todo check this

    @DELETE("admin/auth/terminate")
    suspend fun deleteAccountAdmin(): NMessageResponse
}