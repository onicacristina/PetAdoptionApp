package com.example.petadoptionapp.presentation.ui.home.pet_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentPetDetailsBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.utils.extensions.openEmail
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.showDialer
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class PetDetailsFragment :
    BaseViewBindingFragment<FragmentPetDetailsBinding>(R.layout.fragment_pet_details) {
    override val viewBinding: FragmentPetDetailsBinding by viewBinding(FragmentPetDetailsBinding::bind)
    override val viewModel: PetDetailsViewModel by viewModels()
//    private val args: PetDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adoptionCenterId = arguments?.getString("adoptionCenterId")
        if (adoptionCenterId != null) {
            viewModel.getAdoptionCenterById(adoptionCenterId)
        }

        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {

    }

    private fun initListeners() {
        viewBinding.ivCall.setOnDebounceClickListener {
            showDialer(viewModel.adoptionCenterData.phone)
        }
        viewBinding.ivMailTo.setOnDebounceClickListener {
            openEmail(viewModel.adoptionCenterData.email)
        }
//        viewBinding.ivlocation.setOnDebounceClickListener {
//            // todo open google maps at a location
//        }

        viewBinding.ivlocation.setOnDebounceClickListener {
//            val address = "1600 Amphitheatre Parkway, Mountain View, CA"
            val address = viewModel.adoptionCenterData.getFullAddress()
            val encodedAddress = Uri.encode(address)
            val uri = "geo:0,0?q=$encodedAddress"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.adoptionCenterObservable.collect { data ->
                        Timber.e("adoption center $data")

                    }
                }
            }
            launch {
            }
        }
    }
}

