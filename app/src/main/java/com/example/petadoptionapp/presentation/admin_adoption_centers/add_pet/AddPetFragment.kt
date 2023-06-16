package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentAddPetBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPetFragment :
    NoBottomNavigationFragment<FragmentAddPetBinding>(R.layout.fragment_add_pet) {
    override val viewBinding: FragmentAddPetBinding by viewBinding(
        FragmentAddPetBinding::bind
    )
    override val viewModel: AddPetViewModel by viewModels()

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