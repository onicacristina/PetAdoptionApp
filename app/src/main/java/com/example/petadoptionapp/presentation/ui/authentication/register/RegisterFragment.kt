package com.example.petadoptionapp.presentation.ui.authentication.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentRegisterBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :
    BaseViewBindingFragment<FragmentRegisterBinding>(R.layout.fragment_register) {
    override val viewBinding: FragmentRegisterBinding by viewBinding(FragmentRegisterBinding::bind)
    override val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initViews()
        initObservers()
    }

    private fun initObservers() {
    }

    private fun initViews() {
    }

    private fun initListeners() {
    }

}