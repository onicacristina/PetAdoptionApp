package com.example.petadoptionapp.presentation.ui.home.pet_details.pet_image_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentPetImageDetailsBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetImageDetailsFragment :
    NoBottomNavigationFragment<FragmentPetImageDetailsBinding>(R.layout.fragment_pet_image_details) {

    override val viewBinding: FragmentPetImageDetailsBinding by viewBinding(
        FragmentPetImageDetailsBinding::bind
    )
    override val viewModel: PetImageDetailsViewModel by viewModels()

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