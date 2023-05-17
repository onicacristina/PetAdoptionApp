package com.example.petadoptionapp.presentation.ui.home.pet_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentHomeBinding
import com.example.petadoptionapp.databinding.FragmentPetDetailsBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.home.HomeViewModel
import com.example.petadoptionapp.presentation.ui.home.adapter.HomePetsAdapter
import com.example.petadoptionapp.presentation.ui.home.adapter.PetCategoryAdapterNew
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetDetailsFragment : BaseViewBindingFragment<FragmentPetDetailsBinding>(R.layout.fragment_pet_details) {
    override val viewBinding: FragmentPetDetailsBinding by viewBinding(FragmentPetDetailsBinding::bind)
    override val viewModel: PetDetailsViewModel by viewModels()

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