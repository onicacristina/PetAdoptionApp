package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.select_pet_specie

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import com.example.petadoptionapp.repository.pet_specie_repository.PetSpecieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectPetSpecieViewModel @Inject constructor(
    private val specieRepository: PetSpecieRepository
) : BaseViewModel(),
    StateDelegate<SelectPetSpecieViewModel.State> by DefaultStateDelegate(State.Loading),
    EventDelegate<SelectPetSpecieViewModel.Event> by DefaultEventDelegate() {

    init {
        subscribeToDataObservables()
    }

    private fun subscribeToDataObservables() {
        viewModelScope.launch {
            specieRepository.specieObservable().onEach {
                currentState = State.Value(species = it)
            }.collect()
        }
    }


    fun setClickedSpecie(specie: EPetCategory) {
        specieRepository.setClickedSpecie(specie)
        sendEvent(Event.FINISH)
    }

    sealed class State {
        object Loading : State()
        data class Value(val species: List<EPetCategory>) : State()
    }

    enum class Event {
        FINISH
    }
}