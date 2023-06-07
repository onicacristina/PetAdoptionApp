package com.example.petadoptionapp.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import com.example.petadoptionapp.repository.animals_repository.AnimalsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animalsRepository: AnimalsRepository
) : BaseViewModel(),
    StateDelegate<HomeViewModel.State> by DefaultStateDelegate(State.Loading),
    EventDelegate<HomeViewModel.Event> by DefaultEventDelegate() {

    private val _petCategoryObservable: MutableStateFlow<List<PetCategoryModel>> =
        MutableStateFlow(getPetCategoryList())
    val petCategoryObservable: Flow<List<PetCategoryModel>>
        get() = _petCategoryObservable

    private var specieSelected: EPetCategory = EPetCategory.ALL

    private val searchQueryObservable: MutableStateFlow<String> = MutableStateFlow("")

    private val _searchResults: MutableStateFlow<List<AnimalResponse>> =
        MutableStateFlow(emptyList())
    val searchResults: Flow<List<AnimalResponse>>
        get() = _searchResults


    init {
        getPetsBySpecie(specieSelected)
    }

    fun onSearchChanged(data: String) {
        searchQueryObservable.value = data
        getPetsBySpecie(specieSelected, data)
    }

    fun refresh() {
        getPetsBySpecie(specieSelected)
    }

    fun selectPetCategory(petCategoryModel: PetCategoryModel) {
        val oldData = _petCategoryObservable.value
        val newData = oldData.map { value ->
            val isSelected = value.id == petCategoryModel.id
            value.copy(isSelected = isSelected)
        }
        _petCategoryObservable.value = newData
        specieSelected = EPetCategory.getPetCategoryByIcon(petCategoryModel.iconDrawable)
        getPetsBySpecie(specieSelected)
    }

    private fun getPetsBySpecie(specie: EPetCategory, searchQuery: String = "") {
        viewModelScope.launch {
            currentState = State.Loading
            val response = if (specie != EPetCategory.ALL) {
                animalsRepository.getAnimalsBySpecie(specie.getPetCategoryString())
            } else {
                animalsRepository.getAllAnimals()
            }
            response.fold(
                onFailure = { error ->
                    Timber.e("Failed to fetch pets for specie: $specie, error: $error")
                },
                onSuccess = { pets ->
                    val filteredPets = if (searchQuery.isNotBlank()) {
                        pets.filter { it.name.contains(searchQuery, ignoreCase = true) }
                    } else {
                        pets
                    }
                    Timber.e("Fetched pets for specie: $specie, pets: $filteredPets")
                    currentState =
                        if (filteredPets.isEmpty()) State.Empty else State.Value(filteredPets)
                    _searchResults.value = filteredPets
                }
            )
        }
    }

    private fun getPetCategoryList(): List<PetCategoryModel> {
        return listOf(
            PetCategoryModel(
                0L,
                EPetCategory.ALL.iconResource,
                EPetCategory.ALL.stringResource,
                true
            ),
            PetCategoryModel(
                1L,
                EPetCategory.DOG.iconResource,
                EPetCategory.DOG.stringResource
            ),
            PetCategoryModel(
                2L,
                EPetCategory.CAT.iconResource,
                EPetCategory.CAT.stringResource
            ),
            PetCategoryModel(
                3L,
                EPetCategory.BIRD.iconResource,
                EPetCategory.BIRD.stringResource
            ),
            PetCategoryModel(
                4L,
                EPetCategory.RABBIT.iconResource,
                EPetCategory.RABBIT.stringResource
            ),
        )
    }

    sealed class State {
        object Loading : State()
        object Empty : State()
        data class Value(val petsList: List<AnimalResponse>) : State()
        //TODO: error state
    }

    enum class Event {
        FINISH
    }
}