package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.request.NAnimalParam
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import com.example.petadoptionapp.repository.animals_repository.AnimalsRepository
import com.example.petadoptionapp.repository.pet_gender_repository.PetGenderRepository
import com.example.petadoptionapp.repository.pet_specie_repository.PetSpecieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject


@HiltViewModel
class AddPetViewModel @Inject constructor(
    private val genderRepository: PetGenderRepository,
    private val specieRepository: PetSpecieRepository,
    private val animalsRepository: AnimalsRepository
) : BaseViewModel(),
    StateDelegate<AddPetViewModel.State> by DefaultStateDelegate(State.default),
    EventDelegate<AddPetViewModel.Event> by DefaultEventDelegate() {

    init {
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

    fun addPet() {
        val extraData = mapOf("" to  "")
        val param = NAnimalParam(
            currentState.name,
            currentState.specie.getPetCategoryString(),
            currentState.gender.getPetGenderString(),
            currentState.breed,
            currentState.age.toInt(),
            currentState.vaccinated,
            currentState.neutered,
            currentState.story,
//            currentState.image,
            extraData,
            currentState.adoptionCenterId
        )

        viewModelScope.launch {
            animalsRepository.addAnimal(param).fold(
                onSuccess = {
                    Timber.e("success add pet")
//                    sendEvent(Event.SUCCESS)
                    currentState.image?.let { it1 -> uploadAnimalImage(it.animal.id, it1) }
                },
                onFailure = {
                    Timber.e("error add pet")
                    showError(it)
                }
            )
        }
    }



    private fun uploadAnimalImage(id: String, image: String) {
        viewModelScope.launch {
            val file = File(image)
            val response = animalsRepository.uploadImage(id, image = file).fold(
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
        val isFieldsNotEmpty = currentState.name.isNotEmpty() &&
                isNotEmptySpecie() &&
                isNotEmptyGender() &&
                currentState.breed.isNotEmpty() &&
                currentState.age.isNotEmpty() &&
                currentState.story.isNotEmpty() &&
                currentState.image != null
        currentState = currentState.copy(isEnabledButton = isFieldsNotEmpty)
    }


    fun isNotEmptySpecie(): Boolean {
        if (currentState.specie != EPetCategory.NONE)
            return true
        return false
    }

    fun isNotEmptyGender(): Boolean {
        if (currentState.gender != EPetGender.NONE)
            return true
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
        validateFieldsNotEmpty()
    }

    data class State(
        val name: String,
        val specie: EPetCategory,
        val gender: EPetGender,
        val breed: String,
        val age: String,
        val vaccinated: Boolean,
        val neutered: Boolean,
        val story: String,
        val image: String?,
        val adoptionCenterId: String,
        val isEnabledButton: Boolean = false
    ) {

        companion object {
            val default: State
                get() = State(
                    name = "",
                    specie = EPetCategory.NONE,
                    gender = EPetGender.NONE,
                    breed = "",
                    age = "",
                    vaccinated = false,
                    neutered = false,
                    story = "",
//                    image = "https://images.unsplash.com/photo-1556596187-c3d988ea368c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=773&q=80",
                    image = null,
                    adoptionCenterId = ProfilePrefs().getProfile()?.adoptionCenterId ?: ""
                )
        }

    }

    enum class Event {
        SUCCESS
    }

}