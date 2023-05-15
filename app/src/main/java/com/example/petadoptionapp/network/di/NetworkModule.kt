package com.example.petadoptionapp.network.di

import android.content.Context
import com.example.petadoptionapp.BuildConfig
import com.example.petadoptionapp.network.interceptor.DefaultHeaderInterceptor
import com.example.petadoptionapp.network.interceptor.JwtTokenInterceptor
import com.example.petadoptionapp.network.interceptor.RefreshAuthenticator
import com.example.petadoptionapp.network.refresh_token.RefreshTokenEndpoint
import com.example.petadoptionapp.network.refresh_token.RefreshTokenRepository
import com.example.petadoptionapp.network.services.animals.AnimalsApiInterface
import com.example.petadoptionapp.network.services.animals.AnimalsApiInterfaceImplementation
import com.example.petadoptionapp.network.services.animals.AnimalsApiService
import com.example.petadoptionapp.network.services.auth.AuthApiInterface
import com.example.petadoptionapp.network.services.auth.AuthApiInterfaceImplementation
import com.example.petadoptionapp.network.services.auth.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_API_URL + "api/"

    @Singleton
    @Provides
    fun provideOkHttpClient(
        defaultHeaderInterceptor: DefaultHeaderInterceptor,
        jwtTokenInterceptor: JwtTokenInterceptor,
        refreshAuthenticator: RefreshAuthenticator,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(refreshAuthenticator)
            .addInterceptor(defaultHeaderInterceptor)
            .addInterceptor(jwtTokenInterceptor)
            .addInterceptor(loggingInterceptor)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(10, TimeUnit.SECONDS)
//            .readTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .protocols(listOf(Protocol.HTTP_1_1))
            .connectionPool(ConnectionPool(0, 2, TimeUnit.MINUTES))
            .build()

    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .callFactory(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideJwtTokenInterceptor(tokenRepository: RefreshTokenRepository): JwtTokenInterceptor {
        return JwtTokenInterceptor(tokenRepository)
    }

    @Provides
    @Singleton
    fun provideRefreshAuthenticator(
        refreshTokenEndpoint: RefreshTokenEndpoint,
        @ApplicationContext appContext: Context
    ): RefreshAuthenticator {
        return RefreshAuthenticator(refreshTokenEndpoint, appContext)
    }

    @Provides
    fun provideDefaultHeaderInterceptor(): DefaultHeaderInterceptor {
        return DefaultHeaderInterceptor()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApiInterface(authApiInterface: AuthApiInterfaceImplementation): AuthApiInterface {
        return authApiInterface
    }

    @Provides
    @Singleton
    fun provideAnimalsApiService(retrofit: Retrofit): AnimalsApiService {
        return retrofit.create(AnimalsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAnimalsApiInterface(animalsApiInterface: AnimalsApiInterfaceImplementation): AnimalsApiInterface {
        return animalsApiInterface
    }


}