package com.example.petadoptionapp.presentation.ui.favorite

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.Animal
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import com.example.petadoptionapp.repository.favorites_repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : BaseViewModel(),
    StateDelegate<FavoriteViewModel.State> by DefaultStateDelegate(State.Loading),
    EventDelegate<FavoriteViewModel.Event> by DefaultEventDelegate() {

    init {
        subscribeToDataObservables()
    }

    fun deleteFromFavoritesList(data: Animal) {
        favoritesRepository.deleteFromFavoritesList(data)
    }

    private fun subscribeToDataObservables() {
        viewModelScope.launch {
            favoritesRepository.getFavoritesListObservable()
                .onEach { value ->
                    currentState = if (value.isEmpty()) State.Empty else State.Value(value)
                }
                .onStart {
                    val data = favoritesRepository.getFavoritesList()
                    currentState = if (data.isEmpty()) State.Empty else State.Value(data)
                }
                .launchIn(viewModelScope)
        }
    }

    fun clearFavoritesList() {
        favoritesRepository.clearAllFavoritesList()
    }


    sealed class State {
        object Loading : State()
        object Empty : State()
        data class Value(val petsList: List<Animal>) : State()
    }

    enum class Event {
        FINISH
    }
}