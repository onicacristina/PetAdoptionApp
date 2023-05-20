package com.example.petadoptionapp.presentation.ui.home.pet_details.pet_image_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentPetImageDetailsBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.Constants
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetImageDetailsFragment :
    NoBottomNavigationFragment<FragmentPetImageDetailsBinding>(R.layout.fragment_pet_image_details) {

    override val viewBinding: FragmentPetImageDetailsBinding by viewBinding(
        FragmentPetImageDetailsBinding::bind
    )
    override val viewModel: PetImageDetailsViewModel by viewModels()
    private lateinit var imageUrl: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageUrl = arguments?.getString(Constants.PET_IMAGE_URL).toString()
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        Glide.with(viewBinding.myZoomageView.context).load(imageUrl).into(viewBinding.myZoomageView)
    }

    private fun initListeners() {
        viewBinding.ivBack.setOnDebounceClickListener {
            navController.popBackStack()
        }
    }

    private fun initObservers() {

    }
}