package com.example.petadoptionapp.presentation.ui.profile.edit_profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentEditProfileBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment :
    NoBottomNavigationFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {
    override val viewBinding: FragmentEditProfileBinding by viewBinding(FragmentEditProfileBinding::bind)
    override val viewModel: EditProfileViewModel by viewModels()

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