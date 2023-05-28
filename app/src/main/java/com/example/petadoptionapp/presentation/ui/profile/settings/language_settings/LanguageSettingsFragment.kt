package com.example.petadoptionapp.presentation.ui.profile.settings.language_settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentLanguageSettingsBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageSettingsFragment :
    NoBottomNavigationFragment<FragmentLanguageSettingsBinding>(R.layout.fragment_language_settings) {
    override val viewBinding: FragmentLanguageSettingsBinding by viewBinding(
        FragmentLanguageSettingsBinding::bind
    )
    override val viewModel: LanguageSettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {

    }

    private fun initListeners() {

    }

    private fun initObservers() {

    }
}