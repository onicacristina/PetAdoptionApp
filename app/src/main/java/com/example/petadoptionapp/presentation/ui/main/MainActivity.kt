package com.example.petadoptionapp.presentation.ui.main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.ActivityMainBinding
import com.example.petadoptionapp.network.models.EUserRole
import com.example.petadoptionapp.presentation.base.BaseActivity
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.ui.profile.settings.EAppTheme
import com.example.petadoptionapp.presentation.utils.AppStateFlagsPrefs
import com.example.petadoptionapp.presentation.utils.Constants.REFRESH_TOKEN_EXPIRED
import com.example.petadoptionapp.presentation.utils.LogoutUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (!navController.popBackStack()) {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        setAppMode()
        //TODO("add refresh token if needed")
        solveRefreshTokenIfNeeded()
    }

    fun initNavigation() {
        val graph = navController.navInflater.inflate(R.navigation.nav_main)
        val startDestination = when {
            ProfilePrefs().isLoggedIn() -> goToHome()
            !AppStateFlagsPrefs().showTutorial() -> R.id.selectUserRoleFragment
//            !AppStateFlagsPrefs().showTutorial() -> R.id.loginFragment
            else -> R.id.languageFragment
        }

        graph.setStartDestination(startDestination)
        navController.setGraph(graph, null)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        binding.navView.setupWithNavController(navController)

        solveTabs()
    }

    fun initNavigationFromAppointments() {
        val graph = navController.navInflater.inflate(R.navigation.nav_main)
        val startDestination = when {
            ProfilePrefs().isLoggedIn() -> R.id.appointmentsFragment
            !AppStateFlagsPrefs().showTutorial() -> R.id.loginFragment
            else -> R.id.languageFragment
        }

        graph.setStartDestination(startDestination)
        navController.setGraph(graph, null)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        binding.navView.setupWithNavController(navController)
    }

    fun bottomNavigationVisibility(visibility: Int) {
        binding.navView.visibility = visibility
    }

    private fun solveRefreshTokenIfNeeded() {
        val restart = intent?.getBooleanExtra(REFRESH_TOKEN_EXPIRED, false) ?: false
        if (restart) {
            LogoutUtils().logout()
            navController.popBackStack(R.id.loginFragment, true)
        }
    }

    fun setAppMode() {
        when (getAppThemeFromPrefs()) {
            EAppTheme.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            EAppTheme.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            EAppTheme.SYSTEM_DEFAULT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun getAppThemeFromPrefs(): EAppTheme {
        return ProfilePrefs().getAppTheme()
    }

    private fun solveTabs() {
        val userRole = ProfilePrefs().getUserRole()
        if (userRole == EUserRole.NORMAL_USER)
            switchToNormalUser()
        if (userRole == EUserRole.ADOPTION_CENTER_USER)
            switchToAdminUser()
    }

    private fun switchToNormalUser() {
        binding.navView.menu.clear()
        binding.navView.inflateMenu(R.menu.bottom_nav_menu)
    }

    private fun switchToAdminUser() {
        binding.navView.menu.clear()
        binding.navView.inflateMenu(R.menu.bottom_nav_menu_admin)
    }

    private fun goToHome(): Int {
        val userRole = ProfilePrefs().getUserRole()
        if (userRole == EUserRole.ADOPTION_CENTER_USER)
            return R.id.homeAdminFragment
        return R.id.homeFragment
    }
}