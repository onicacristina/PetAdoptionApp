package com.example.petadoptionapp.presentation.ui.profile.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentSettingsBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment :
    NoBottomNavigationFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {
    override val viewBinding: FragmentSettingsBinding by viewBinding(
        FragmentSettingsBinding::bind
    )
    override val viewModel: SettingsViewModel by viewModels()

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