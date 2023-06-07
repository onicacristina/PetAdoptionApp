package com.example.petadoptionapp.presentation.ui.favorite

import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(

) : BaseViewModel(),
    StateDelegate<FavoriteViewModel.State> by DefaultStateDelegate(State.Loading),
    EventDelegate<FavoriteViewModel.Event> by DefaultEventDelegate() {

    init {
        getFavoritesList()
    }

    private fun getFavoritesList() {
//        val favorites =  emptyList<AnimalResponse>()
        val favorites = listOf<AnimalResponse>(
            AnimalResponse("0af23460-f372-11ed-93ea-13cb0af60365",
            specie = EPetCategory.BIRD,
            gender = EPetGender.MALE,
            name = "Birda",
            breed = "breed",
            age = 0,
            vaccinated = true,
            neutered = false,
            story = "Hello there i am a bird and i am happy.",
            imageUrl = "https://images.unsplash.com/photo-1580775404530-d559b4e32494?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            adoptionCenterId = "70aa6960-f199-11ed-9bc8-e70506774611",
            isSaved = true),
            AnimalResponse("0af23460-f372-11ed-93ea-13cb0af60365",
                specie = EPetCategory.DOG,
                gender = EPetGender.MALE,
                name = "Birda",
                breed = "breed",
                age = 0,
                vaccinated = true,
                neutered = false,
                story = "Hello there i am a bird and i am happy.",
                imageUrl = "https://images.unsplash.com/photo-1561037404-61cd46aa615b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                adoptionCenterId = "70aa6960-f199-11ed-9bc8-e70506774611",
                isSaved = true)
        )
        currentState = if (favorites.isEmpty()) State.Empty else State.Value(favorites)
    }

    sealed class State {
        object Loading : State()
        object Empty : State()
        data class Value(val petsList: List<AnimalResponse>) : State()
    }

    enum class Event {
        FINISH
    }
}