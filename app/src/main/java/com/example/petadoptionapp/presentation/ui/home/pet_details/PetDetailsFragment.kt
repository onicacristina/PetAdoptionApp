package com.example.petadoptionapp.presentation.ui.home.pet_details

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.example.petadoptionapp.network.models.EUserRole
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.presentation.utils.Constants
import com.example.petadoptionapp.presentation.utils.LocaleHelper
import com.example.petadoptionapp.presentation.utils.extensions.*
import com.example.petadoptionapp.presentation.utils.showDialog
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
        initFavoriteVisibility()
        initBottomButtons()
        initAdoptionCenterLayout()
    }

    private fun initListeners() {
        viewBinding.ivPetImage.setOnDebounceClickListener {
            val bundle = Bundle()
            val imageUrl = if (viewModel.animalData.uploadedAssets.isNotEmpty()) {
                viewModel.animalData.uploadedAssets.first().path
            } else {
                Constants.PLACEHOLDER_PET_IMAGE
            }
            bundle.putString(Constants.PET_IMAGE_URL, imageUrl)
            navController.navigate(
                R.id.action_petDetailsFragment_to_petImageDetailsFragment,
                bundle
            )
        }
        viewBinding.ivBack.setOnDebounceClickListener {
            navController.popBackStack()
        }
        viewBinding.ivFavorite.setOnDebounceClickListener {
            viewModel.onFavoriteClicked()
        }
        viewBinding.ivCall.setOnDebounceClickListener {
            showDialer(viewModel.adoptionCenterData.phone)
        }
        viewBinding.ivChat.setOnDebounceClickListener {
            openEmail(viewModel.adoptionCenterData.email)
        }
        viewBinding.btnAdoptNow.setOnDebounceClickListener {
            openBookAppointmentScreen(pet = pet, adoptionCenter = adoptionCenter)
        }
        viewBinding.tvAdoptionCenter.setOnDebounceClickListener {
            //todo
        }

        viewBinding.tvAdoptionCenterAddress.setOnDebounceClickListener {
            openGoogleMaps(viewModel.adoptionCenterData.getFullAddress())
        }

        viewBinding.btnDelete.setOnDebounceClickListener {
            showDeleteDialog()
        }

        viewBinding.btnEdit.setOnDebounceClickListener {
            openEditPetScreen()
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
                launch {
                    viewModel.isSavedToFavorites.collect { data ->
                        data?.let { initPetFavoriteIcon(it) }
                    }
                }
                launch {
                    viewModel.event.collect { event ->
                        onEvent(event)
                    }
                }
            }
        }
    }

    private fun onEvent(event: PetDetailsViewModel.Event) {
        when (event) {
            PetDetailsViewModel.Event.SAVED_TO_FAVORITES -> onSavedToFavorites()
            PetDetailsViewModel.Event.REMOVED_FROM_FAVORITES -> onRemovedFromFavorites()
            PetDetailsViewModel.Event.PET_REMOVED -> navController.popBackStack()
        }
    }

    private fun onSavedToFavorites() {
        Toast.makeText(
            requireContext(),
            getString(R.string.animal_added_to_favorites),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun onRemovedFromFavorites() {
        Toast.makeText(
            requireContext(),
            getString(R.string.animal_removed_from_favorites),
            Toast.LENGTH_LONG
        ).show()
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
        initAddedModifiedVisibility(data)
    }

    private fun initPetFavoriteIcon(isFavorite: Boolean) {
        viewBinding.ivFavorite.setImageResource(if (isFavorite) R.drawable.ic_favorite_selected else R.drawable.ic_favorite)
    }

    private fun initAdoptionCenterDetails(data: AdoptionCenter) {
        adoptionCenter = data
        initAdoptionCenterName(data)
        initAdoptionCenterFullAddress(data)
    }

    private fun initPetImage(data: AnimalResponse) {
        if (data.uploadedAssets.isNotEmpty()) {
            val imageUrl = data.uploadedAssets.first().path ?: ""
            Glide.with(viewBinding.ivPetImage.context).load(imageUrl).into(viewBinding.ivPetImage)
        } else {
            Glide.with(viewBinding.ivPetImage.context).load(Constants.PLACEHOLDER_PET_IMAGE)
                .into(viewBinding.ivPetImage)
        }
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

    private fun initFavoriteVisibility() {
        viewBinding.ivFavorite.isVisible = !isAdmin()
    }

    private fun initBottomButtons() {
        viewBinding.clButtonsAdmin.isVisible = isAdmin()
        viewBinding.clButtonsNormalUser.isVisible = !isAdmin()
    }

    private fun initAdoptionCenterLayout() {
        val isAdmin = isAdmin()
        viewBinding.tvAdoptionCenter.isVisible = !isAdmin
        viewBinding.tvAdoptionCenterName.isVisible = !isAdmin
        viewBinding.tvAdoptionCenterAddress.isVisible = !isAdmin
    }

    private fun initAddedModifiedVisibility(data: AnimalResponse) {
        val isAdmin = isAdmin()
        if (isAdmin) {
            viewBinding.clAddedAtInfo.isVisible = true
            viewBinding.clLastModifiedInfo.isVisible = true
            viewBinding.tvAddedAt.text =
                data.getFormattedDate(LocaleHelper.getLocale().locale, data.createdAt)
            viewBinding.tvLastModifiedAt.text =
                data.getFormattedDate(LocaleHelper.getLocale().locale, data.updatedAt)
        } else {
            viewBinding.clAddedAtInfo.isVisible = false
            viewBinding.clLastModifiedInfo.isVisible = false
        }
    }

    private fun isAdmin(): Boolean {
        val userRole = ProfilePrefs().getUserRole()
        return userRole == EUserRole.ADOPTION_CENTER_USER
    }

    private fun openEditPetScreen() {
        val pet = viewModel.animalData
        navController.navigate(
            R.id.action_petDetailsFragment_to_editPetFragment,
            bundleOf(Constants.PET_DETAILS_TO_EDIT to pet)
        )
    }

    private fun showDeleteDialog() {
        showDialog(
            requireContext(),
            getString(R.string.delete_pet),
            getString(R.string.delete_pet_description),
            R.drawable.btn_rounded_red,
            getString(R.string.delete),
            {
                viewModel.deletePet()
            },
            getString(R.string.cancel),
            null,
        )
    }

    override fun onResume() {
        super.onResume()
//        viewModel.getAnimalDetails(viewModel.animalData.id)
    }

}

