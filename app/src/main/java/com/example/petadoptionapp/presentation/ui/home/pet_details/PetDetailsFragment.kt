package com.example.petadoptionapp.presentation.ui.home.pet_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentPetDetailsBinding
import com.example.petadoptionapp.network.models.AdoptionCenter
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.presentation.utils.Constants
import com.example.petadoptionapp.presentation.utils.extensions.openEmail
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.showDialer
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class PetDetailsFragment :
    NoBottomNavigationFragment<FragmentPetDetailsBinding>(R.layout.fragment_pet_details) {
    override val viewBinding: FragmentPetDetailsBinding by viewBinding(FragmentPetDetailsBinding::bind)
    override val viewModel: PetDetailsViewModel by viewModels()
    private lateinit var pet: AnimalResponse
    private lateinit var adoptionCenter: AdoptionCenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adoptionCenterId = arguments?.getString(Constants.ADOPTION_CENTER_ID)
        val petId = arguments?.getString(Constants.PET_ID)
        if (adoptionCenterId != null) {
            viewModel.getAdoptionCenterById(adoptionCenterId)
        }
        if (petId != null) {
            viewModel.getAnimalDetails(petId)
        }

        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {

    }

    private fun initListeners() {
        viewBinding.ivPetImage.setOnDebounceClickListener {
            val bundle = Bundle()
            bundle.putString(Constants.PET_IMAGE_URL, viewModel.animalData.imageUrl)
            navController.navigate(
                R.id.action_petDetailsFragment_to_petImageDetailsFragment,
                bundle
            )
        }
        viewBinding.ivBack.setOnDebounceClickListener {
            navController.popBackStack()
        }
        viewBinding.ivFavorite.setOnDebounceClickListener {
            //TODO
        }
        viewBinding.ivCall.setOnDebounceClickListener {
            showDialer(viewModel.adoptionCenterData.phone)
        }
        viewBinding.ivChat.setOnDebounceClickListener {
            openEmail(viewModel.adoptionCenterData.email)
        }
        viewBinding.btnAdoptNow.setOnDebounceClickListener {
            openBookAppointmentScreen(pet = pet, adoptionCenter = adoptionCenter)
//            navController.navigate(R.id.action_petDetailsFragment_to_bookAppointmentFragment)
        }
        viewBinding.tvAdoptionCenter.setOnDebounceClickListener {
            //todo
        }

        viewBinding.tvAdoptionCenterAddress.setOnDebounceClickListener {
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
                        data?.let { initAdoptionCenterDetails(data) }
                    }
                }
                launch {
                    viewModel.animalObservable.collect { data ->
                        data?.let { initPetDetails(it) }
                    }
                }
            }
        }
    }

    private fun openBookAppointmentScreen(pet: AnimalResponse, adoptionCenter: AdoptionCenter) {
        navController.navigate(
            R.id.action_petDetailsFragment_to_bookAppointmentFragment,
            bundleOf(
                Constants.PET to pet,
                Constants.ADOPTION_CENTER to adoptionCenter
            )
        )
    }

    private fun initPetDetails(data: AnimalResponse) {
        pet = data
        initPetImage(data)
        initPetName(data)
        initPetBreed(data)
        initPetAge(data)
        initPetGender(data)
        initPetHealth(data)
        initPetStory(data)
    }

    private fun initAdoptionCenterDetails(data: AdoptionCenter) {
        adoptionCenter = data
        initAdoptionCenterName(data)
        initAdoptionCenterFullAddress(data)
    }

    private fun initPetImage(data: AnimalResponse) {
        Glide.with(viewBinding.ivPetImage.context).load(data.imageUrl).into(viewBinding.ivPetImage)
    }

    private fun initPetName(data: AnimalResponse) {
        viewBinding.tvPetName.text = data.name
    }

    private fun initPetBreed(data: AnimalResponse) {
        viewBinding.tvPetBreed.text = data.breed
    }

    private fun initPetAge(data: AnimalResponse) {
        viewBinding.tvPetAge.text = getString(R.string.pet_age_in_months, data.age.toString())
    }

    private fun initPetGender(data: AnimalResponse) {
        viewBinding.tvPetGender.text = getString(data.gender.stringResource)
        if (data.gender == EPetGender.FEMALE) {
            viewBinding.ivPetGender.setImageResource(EPetGender.FEMALE.iconResource)
            viewBinding.viewPet.setBackgroundResource(R.drawable.bg_female_gender)
        } else {
            viewBinding.ivPetGender.setImageResource(EPetGender.MALE.iconResource)
            viewBinding.viewPet.setBackgroundResource(R.drawable.bg_male_gender)
        }
    }

    private fun initPetHealth(data: AnimalResponse) {
        val isVaccinated = data.vaccinated
        val isNeutered = data.neutered
        viewBinding.tvHealth.isVisible = isVaccinated || isNeutered
        if (isVaccinated) {
            viewBinding.tvVaccinated.isVisible = true
            viewBinding.tvVaccinated.text = getString(R.string.vaccinated)
        } else
            viewBinding.tvVaccinated.isVisible = false

        if (isNeutered) {
            viewBinding.tvNeutered.isVisible = true
            viewBinding.tvNeutered.text = getString(R.string.neutered)
        } else
            viewBinding.tvNeutered.isVisible = false
    }

    private fun initPetStory(data: AnimalResponse) {
        viewBinding.tvStory.text = data.story
    }

    private fun initAdoptionCenterName(data: AdoptionCenter) {
        viewBinding.tvAdoptionCenterName.text = getString(R.string.adoption_center_name, data.name)
    }

    private fun initAdoptionCenterFullAddress(data: AdoptionCenter) {
        viewBinding.tvAdoptionCenterAddress.text = data.getFullAddress()
    }
}

