package com.example.petadoptionapp.presentation.ui.favorite

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.ui.home.EPetGender
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : BaseViewModel(),
    StateDelegate<FavoriteViewModel.State> by DefaultStateDelegate(State.Loading),
    EventDelegate<FavoriteViewModel.Event> by DefaultEventDelegate() {

    init {
        getFavoritesList()
//        subscribeToDataObservables()
    }

    fun deleteFromFavoritesList(data: AnimalResponse) {
        favoritesRepository.deleteFromFavoritesList(data)
    }

    private fun subscribeToDataObservables() {
        viewModelScope.launch {
            favoritesRepository.getFavoritesListObservable()
                .onEach { value ->
                    Timber.e("am ajuns aici")
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

    private fun getFavoritesList() {
        val favorites =  emptyList<AnimalResponse>()

//        val a2 = AnimalResponse(
//            "2",
//            EPetCategory.DOG,
//            EPetGender.FEMALE,
//            name = "Bella",
//            breed = "Corgi",
//            age = 24,
//            vaccinated = true,
//            neutered = true,
//            story = "Corgis are loving, loyal, and extremely friendly, often referred to as \"little royals\" due to their regal and charismatic demeanor. With their perpetual smile and wagging tail, corgis have the ability to melt hearts with their charm. Whether they're seeking adventures or learning new tricks, corgis are always ready to embrace life and provide unconditional love.",
//            imageUrl = "https://images.unsplash.com/photo-1554693190-383dd5302125?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=871&q=80",
//            adoptionCenterId = "81925700-13af-11ee-85fb-a932987c84ca",
//            createdAt = "2023-06-25T23:25:00.912Z",
//            updatedAt = "2023-06-25T23:25:00.912Z"
//        )
//
//        val a3 = AnimalResponse(
//            "3",
//            EPetCategory.BIRD,
//            EPetGender.FEMALE,
//            name = "Henrietta",
//            breed = "Common breed",
//            age = 2,
//            vaccinated = false,
//            neutered = false,
//            story = "Henrietta is a chicken with shiny golden feathers and a wise eye. She has been through a lot in her life, and now it's time for her to find a family who will welcome her with open arms.",
//            imageUrl = "https://images.unsplash.com/photo-1580866177074-5c0b1b924d32?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80",
//            adoptionCenterId = "81925700-13af-11ee-85fb-a932987c84ca",
//            createdAt = "2023-06-25T23:25:00.912Z",
//            updatedAt = "2023-06-25T23:25:00.912Z"
//        )
//
//        val favorites = listOf<AnimalResponse>(
//            AnimalResponse(
//                "2",
//                EPetCategory.DOG,
//                EPetGender.FEMALE,
//                name = "Bella",
//                breed = "Corgi",
//                age = 24,
//                vaccinated = true,
//                neutered = true,
//                story = "Corgis are loving, loyal, and extremely friendly, often referred to as \"little royals\" due to their regal and charismatic demeanor. With their perpetual smile and wagging tail, corgis have the ability to melt hearts with their charm. Whether they're seeking adventures or learning new tricks, corgis are always ready to embrace life and provide unconditional love.",
//                imageUrl = "https://images.unsplash.com/photo-1554693190-383dd5302125?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=871&q=80",
//                adoptionCenterId = "81925700-13af-11ee-85fb-a932987c84ca",
//                createdAt = "2023-06-25T23:25:00.912Z",
//                updatedAt = "2023-06-25T23:25:00.912Z"
//            ),
//            AnimalResponse(
//                "3",
//                EPetCategory.BIRD,
//                EPetGender.FEMALE,
//                name = "Henrietta",
//                breed = "Common breed",
//                age = 2,
//                vaccinated = false,
//                neutered = false,
//                story = "Henrietta is a chicken with shiny golden feathers and a wise eye. She has been through a lot in her life, and now it's time for her to find a family who will welcome her with open arms.",
//                imageUrl = "https://images.unsplash.com/photo-1580866177074-5c0b1b924d32?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80",
//                adoptionCenterId = "81925700-13af-11ee-85fb-a932987c84ca",
//                createdAt = "2023-06-25T23:25:00.912Z",
//                updatedAt = "2023-06-25T23:25:00.912Z"
//            )
//        )
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