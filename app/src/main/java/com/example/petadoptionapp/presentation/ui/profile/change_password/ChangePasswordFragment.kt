package com.example.petadoptionapp.presentation.ui.profile.change_password

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentChangePasswordBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment :
    NoBottomNavigationFragment<FragmentChangePasswordBinding>(R.layout.fragment_change_password) {
    override val viewBinding: FragmentChangePasswordBinding by viewBinding(
        FragmentChangePasswordBinding::bind
    )
    override val viewModel: ChangePasswordViewModel by viewModels()

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