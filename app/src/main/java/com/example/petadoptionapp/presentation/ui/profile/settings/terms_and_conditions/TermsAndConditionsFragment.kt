package com.example.petadoptionapp.presentation.ui.profile.settings.terms_and_conditions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentTermsAndConditionsBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding


class TermsAndConditionsFragment :
    NoBottomNavigationFragment<FragmentTermsAndConditionsBinding>(R.layout.fragment_terms_and_conditions) {
    override val viewBinding: FragmentTermsAndConditionsBinding by viewBinding(
        FragmentTermsAndConditionsBinding::bind
    )
    override val viewModel: TermsAndConditionsViewModel by viewModels()

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