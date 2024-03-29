package com.example.petadoptionapp.presentation.admin_adoption_centers.edit_pet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.UploadedAssets
import com.example.petadoptionapp.network.models.request.NAnimalParam
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.presentation.utils.*
import com.example.petadoptionapp.repository.animals_repository.AnimalsRepository
import com.example.petadoptionapp.repository.pet_gender_repository.PetGenderRepository
import com.example.petadoptionapp.repository.pet_specie_repository.PetSpecieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditPetViewModel @Inject constructor(
    private val genderRepository: PetGenderRepository,
    private val specieRepository: PetSpecieRepository,
    private val animalsRepository: AnimalsRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(),
    StateDelegate<EditPetViewModel.State> by DefaultStateDelegate(State.default),
    EventDelegate<EditPetViewModel.Event> by DefaultEventDelegate() {

    private val navArgs: EditPetFragmentArgs by lazy {
        EditPetFragmentArgs.fromSavedStateHandle(savedStateHandle)
    }

    private var isImageChanged: Boolean = false

    init {
        getPetDetails()
        subscribeToClickedGenderObservables()
        subscribeToClickedSpecieObservables()
    }

    private fun subscribeToClickedGenderObservables() {
        viewModelScope.launch {
            genderRepository.clickedGenderObservable().onEach { hour ->
                hour?.let {
                    onGenderChanged(it)
                }
            }.collect()
        }
    }

    private fun subscribeToClickedSpecieObservables() {
        viewModelScope.launch {
            specieRepository.clickedSpecieObservable().onEach { hour ->
                hour?.let {
                    onSpecieChanged(it)
                }
            }.collect()
        }
    }

    private fun getPetDetails() {
        val petImage =
            if (navArgs.petDetailsToEdit.uploadedAssets.isNotEmpty()) navArgs.petDetailsToEdit.uploadedAssets.first().path else Constants.PLACEHOLDER_PET_IMAGE
        currentState = currentState.copy(
            id = navArgs.petDetailsToEdit.id,
            name = navArgs.petDetailsToEdit.name,
            specie = navArgs.petDetailsToEdit.specie,
            gender = navArgs.petDetailsToEdit.gender,
            breed = navArgs.petDetailsToEdit.breed,
            age = navArgs.petDetailsToEdit.age.toString(),
            vaccinated = navArgs.petDetailsToEdit.vaccinated,
            neutered = navArgs.petDetailsToEdit.neutered,
            story = navArgs.petDetailsToEdit.story,
            image = petImage,
            uploadedAssets = navArgs.petDetailsToEdit.uploadedAssets,
            adoptionCenterId = navArgs.petDetailsToEdit.adoptionCenterId,
        )
    }

    fun editAnimal() {
        viewModelScope.launch {
            val extraData = mapOf("" to "")
            val pet = NAnimalParam(
                name = currentState.name,
                specie = currentState.specie.getPetCategoryString(),
                gender = currentState.gender.getPetGenderString(),
                breed = currentState.breed,
                age = currentState.age.toInt(),
                vaccinated = currentState.vaccinated,
                neutered = currentState.neutered,
                story = currentState.story,
                extraData = extraData,
                adoptionCenterId = currentState.adoptionCenterId
            )

            animalsRepository.editAnimal(currentState.id, pet).fold(
                onSuccess = {
                    Timber.e("success to edit pet")
                    imageChangedActions()
                },
                onFailure = { error ->
                    showError(error)
                    Timber.e("error edit pet")
                }
            )
        }
    }

    private fun imageChangedActions() {
        if (isImageChanged) {
            if (currentState.uploadedAssets.isEmpty()) {
                currentState.image?.let { uploadAnimalImage(currentState.id, it) }
            } else {
                deleteImage()
            }

        } else {
            sendEvent(Event.SUCCESS)
        }
    }

    private fun deleteImage() {
        val assetId = currentState.uploadedAssets.first().id
        viewModelScope.launch {
            animalsRepository.deleteAnimalImage(id = currentState.id, assetId = assetId).fold(
                onSuccess = {
                    currentState.image?.let { uploadAnimalImage(currentState.id, it) }
                    Timber.e("success delete image: $error")

                },
                onFailure = { error ->
                    showError(error)
                    Timber.e("error delete image: $error")

                }
            )
        }
    }

    private fun uploadAnimalImage(id: String, image: String) {
        viewModelScope.launch {
            val file = File(image)
            animalsRepository.uploadImage(id, image = file).fold(
                onFailure = { error ->
                    showError(error)
                    Timber.e("error upload image: $error")
                },
                onSuccess = {
                    Timber.e("success upload image")
                    sendEvent(Event.SUCCESS)
                }
            )
        }
    }


    private fun validateFieldsNotEmpty() {
        val isFieldsNotEmpty =
            currentState.name.isNotEmpty() && isNotEmptySpecie() && isNotEmptyGender() && currentState.breed.isNotEmpty() && currentState.age.isNotEmpty() && currentState.story.isNotEmpty() && currentState.image?.isNotEmpty() == true
        currentState = currentState.copy(isEnabledButton = isFieldsNotEmpty)
    }

    private fun isNotEmptySpecie(): Boolean {
        if (currentState.specie != EPetCategory.NONE) return true
        return false
    }

    private fun isNotEmptyGender(): Boolean {
        if (currentState.gender != EPetGender.NONE) return true
        return false
    }

    fun onNameChanges(data: String) {
        currentState = currentState.copy(name = data)
        validateFieldsNotEmpty()
    }

    private fun onSpecieChanged(data: EPetCategory) {
        currentState = currentState.copy(specie = data)
        validateFieldsNotEmpty()
    }

    private fun onGenderChanged(data: EPetGender) {
        currentState = currentState.copy(gender = data)
        validateFieldsNotEmpty()
    }

    fun onBreedChanged(data: String) {
        currentState = currentState.copy(breed = data)
        validateFieldsNotEmpty()
    }

    fun onAgeChanged(data: String) {
        currentState = currentState.copy(age = data)
        validateFieldsNotEmpty()
    }

    fun onVaccinatedChanged(data: Boolean) {
        currentState = currentState.copy(vaccinated = data)
        validateFieldsNotEmpty()
    }

    fun onNeuteredChanged(data: Boolean) {
        currentState = currentState.copy(neutered = data)
        validateFieldsNotEmpty()
    }

    fun onStoryChanged(data: String) {
        currentState = currentState.copy(story = data)
        validateFieldsNotEmpty()
    }

    fun onImageChanged(data: String) {
        currentState = currentState.copy(image = data)
        isImageChanged = true
        validateFieldsNotEmpty()
    }

    data class State(
        val id: String,
        val name: String,
        val specie: EPetCategory,
        val gender: EPetGender,
        val breed: String,
        val age: String,
        val vaccinated: Boolean,
        val neutered: Boolean,
        val story: String,
        val image: String? = "",
        val uploadedAssets: List<UploadedAssets>,
        val adoptionCenterId: String,
        val isEnabledButton: Boolean = false
    ) {

        companion object {
            val default: State
                get() = State(
                    id = "",
                    name = "",
                    specie = EPetCategory.NONE,
                    gender = EPetGender.NONE,
                    breed = "",
                    age = "",
                    vaccinated = false,
                    neutered = false,
                    story = "",
                    image = Constants.PLACEHOLDER_PET_IMAGE,
                    uploadedAssets = listOf(),
                    adoptionCenterId = "70aa6960-f199-11ed-9bc8-e70506774611"
                )
        }
    }

    enum class Event {
        SUCCESS
    }

}