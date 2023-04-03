package com.example.petadoptionapp.network.refresh_token

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val AUTHENTICATION_REQUIRED = "AUTHENTICATION_REQUIRED"

class RefreshTokenEndpoint @Inject constructor(
    private val tokenRepository: RefreshTokenRepository,
    private val BASE_URL: String
) {

    fun refreshToken(): String? {
        val refreshToken = "Bearer " + tokenRepository.getRefreshToken()
        refreshToken?.let { return doRefreshToken(it) }
        return ""
    }

    private fun doRefreshToken(refreshToken: String): String? {
        val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level =
                HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .protocols(listOf(Protocol.HTTP_1_1))
            .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        Timber.d("Refresh Token: make request")

        val refreshTokenApiService: RefreshTokenService =
            retrofit.create(RefreshTokenService::class.java)
        val response = refreshTokenApiService.refreshToken(refreshToken).execute()

        val body = response.body()
        val newAccessToken = body?.token
        val newRefreshToken = body?.refreshToken

        newAccessToken?.let { tokenRepository.saveAccessToken(it) }
        newRefreshToken?.let { tokenRepository.saveRefreshToken(it) }

        if (response.code() == 401)
            return AUTHENTICATION_REQUIRED

        return response.body()?.token
    }
}