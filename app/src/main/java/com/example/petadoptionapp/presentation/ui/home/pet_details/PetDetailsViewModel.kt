package com.example.petadoptionapp.presentation.ui.home.pet_details

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.AdoptionCenter
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.repository.adoption_center_repository.AdoptionCenterRepository
import com.example.petadoptionapp.repository.animals_repository.AnimalsRepository
import com.example.petadoptionapp.repository.favorites_repository.FavoritesRepository
import com.example.petadoptionapp.repository.mapper.responses.AnimalMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PetDetailsViewModel @Inject constructor(
    private val animalsRepository: AnimalsRepository,
    private val adoptionCenterRepository: AdoptionCenterRepository,
    private val favoritesRepository: FavoritesRepository
) : BaseViewModel(),
    EventDelegate<PetDetailsViewModel.Event> by DefaultEventDelegate() {

    var adoptionCenterData: AdoptionCenter = AdoptionCenter.default
    var animalData: AnimalResponse = AnimalResponse.default

    private val _adoptionCenterObservable: MutableStateFlow<AdoptionCenter?> =
        MutableStateFlow(null)
    val adoptionCenterObservable: Flow<AdoptionCenter?>
        get() = _adoptionCenterObservable

    private val _animalObservable: MutableStateFlow<AnimalResponse?> = MutableStateFlow(null)
    val animalObservable: Flow<AnimalResponse?>
        get() = _animalObservable

    private val _isSavedToFavorites: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isSavedToFavorites: Flow<Boolean?>
        get() = _isSavedToFavorites


    init {
        favoritesRepository.getFavoritesList()
//        checkIsSavedToFavoritesList()
    }

    private fun onIsSavedToFavoriteChanged() {
        _isSavedToFavorites.value = !_isSavedToFavorites.value!!
    }

    private fun checkIsSavedToFavoritesList() {
        viewModelScope.launch {
            val data = AnimalMapper().map(animalData)
            _isSavedToFavorites.value = favoritesRepository.isSavedToFavoritesList(data)
            Timber.e("isSaved ${_isSavedToFavorites.value}")
        }
    }


    fun onFavoriteClicked() {
        if (_isSavedToFavorites.value == true) {
            // The animal is already saved in the favorites list
            removeFromFavoritesList()
            sendEvent(Event.REMOVED_FROM_FAVORITES)
            onIsSavedToFavoriteChanged()
        } else {
            // The animal is not saved in the favorites list
            addToFavoritesList()
            sendEvent(Event.SAVED_TO_FAVORITES)
            onIsSavedToFavoriteChanged()
        }
    }


    private fun addToFavoritesList() {
        val data = AnimalMapper().map(animalData)
        favoritesRepository.saveToFavorites(data)
    }

    private fun removeFromFavoritesList() {
        val data = AnimalMapper().map(animalData)
        favoritesRepository.deleteFromFavoritesList(data)
    }

    fun getAdoptionCenterById(id: String) {
        viewModelScope.launch {
            adoptionCenterRepository.getOneAdoptionCenterById(id).fold(
                onSuccess = { adoptionCenter ->
                    _adoptionCenterObservable.value = adoptionCenter
                    adoptionCenterData = adoptionCenter
                    Timber.e("success get adoption center $adoptionCenter")
                },
                onFailure = { error ->
                    Timber.e("error get adoption center")
                    showError(error)
                }
            )
        }
    }

    fun getAnimalDetails(id: String) {
        viewModelScope.launch {
            animalsRepository.getOneAnimalById(id).fold(
                onSuccess = { animal ->
                    _animalObservable.value = animal
                    animalData = animal
                    checkIsSavedToFavoritesList()
                    Timber.e("success get animal $animal")

                },
                onFailure = { error ->
                    Timber.e("error get animal")
                    showError(error)
                }
            )
        }
    }

    fun deletePet() {
        viewModelScope.launch {
            animalsRepository.deleteAnimal(id = animalData.id).fold(
                onSuccess = {
                    Timber.e("animal was deleted")
                    sendEvent(Event.PET_REMOVED)
                },
                onFailure = { error ->
                    Timber.e("error to delete pet")
                    showError(error)
                }
            )
        }
    }

    enum class Event {
        SAVED_TO_FAVORITES,
        REMOVED_FROM_FAVORITES,
        PET_REMOVED
    }
}