package com.example.petadoptionapp.presentation.ui.language

import android.os.Bundle
import android.view.View
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentLanguageBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.utils.AppStateFlagsPrefs
import com.example.petadoptionapp.presentation.utils.LocaleHelper
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding

class LanguageFragment :
    NoBottomNavigationFragment<FragmentLanguageBinding>(R.layout.fragment_language) {
    override val viewBinding: FragmentLanguageBinding by viewBinding(FragmentLanguageBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startNavigationDestination()
    }

    private fun startNavigationDestination() {
        navController.popBackStack()
        if (ProfilePrefs().isLoggedIn())
            navController.navigate(R.id.homeFragment)
        else
            if (!AppStateFlagsPrefs().showTutorial())
                navController.navigate(R.id.loginFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        viewBinding.btnRomanian.setOnClickListener {
            listenerFunction(ELanguage.ROMANIAN)
        }

        viewBinding.btnEnglish.setOnClickListener {
            listenerFunction(ELanguage.ENGLISH)
        }
    }

    private fun selectLanguage(language: ELanguage = ELanguage.ROMANIAN) {
        LocaleHelper.setLocale(language)
        openOnBoarding()
    }

    private fun listenerFunction(language: ELanguage) {
        selectLanguage(language)
    }

    private fun openOnBoarding() {
        navController.popBackStack()
        if (AppStateFlagsPrefs().showTutorial())
            navController.navigate(R.id.onBoardingFragment)
        else
            navController.navigate(R.id.loginFragment)
    }
}