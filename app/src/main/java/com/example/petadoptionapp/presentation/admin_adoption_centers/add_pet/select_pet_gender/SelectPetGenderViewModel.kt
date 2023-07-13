package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.select_pet_gender

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import com.example.petadoptionapp.repository.pet_gender_repository.PetGenderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectPetGenderViewModel @Inject constructor(
    private val genderRepository: PetGenderRepository
) : BaseViewModel(),
    StateDelegate<SelectPetGenderViewModel.State> by DefaultStateDelegate(State.Loading),
    EventDelegate<SelectPetGenderViewModel.Event> by DefaultEventDelegate() {

    init {
        subscribeToDataObservables()
    }

    private fun subscribeToDataObservables() {
        viewModelScope.launch {
            genderRepository.genderObservable().onEach {
                currentState = State.Value(genders = it)
            }.collect()
        }
    }


    fun setClickedGender(gender: EPetGender) {
        genderRepository.setClickedGender(gender)
        sendEvent(Event.FINISH)
    }

    sealed class State {
        object Loading : State()
        data class Value(val genders: List<EPetGender>) : State()
    }

    enum class Event {
        FINISH
    }
}