package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import com.example.petadoptionapp.repository.pet_gender_repository.PetGenderRepository
import com.example.petadoptionapp.repository.pet_specie_repository.PetSpecieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddPetViewModel @Inject constructor(
    private val genderRepository: PetGenderRepository,
    private val specieRepository: PetSpecieRepository
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

    private fun validateFieldsNotEmpty() {
        val isFieldsNotEmpty = currentState.name.isNotEmpty() &&
                isNotEmptySpecie() &&
                isNotEmptyGender() &&
                currentState.breed.isNotEmpty() &&
                currentState.age.isNotEmpty() &&
                currentState.story.isNotEmpty() &&
                currentState.image.isNotEmpty()
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
        val image: String,
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
                    image = "",
                    adoptionCenterId = ""
                )
        }

    }

    enum class Event {
        SUCCESS
    }

}