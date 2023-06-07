package com.example.petadoptionapp.presentation.ui.favorite

import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.base.BaseViewModel
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
        val favorites =  emptyList<AnimalResponse>()
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