package com.example.petadoptionapp.presentation.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.petadoptionapp.R
import dagger.hilt.android.AndroidEntryPoint

const val REFRESH_TOKEN_EXPIRED = "REFRESH_TOKEN_EXPIRED"

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        //TODO("add refresh token if needed")
//        solveRefreshTokenIfNeeded()
    }
}