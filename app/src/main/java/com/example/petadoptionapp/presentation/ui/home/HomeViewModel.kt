package com.example.petadoptionapp.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.utils.Resource
import com.example.petadoptionapp.repository.AnimalsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animalsRepository: AnimalsRepository
) : BaseViewModel() {

    private val _petCategoryObservable: MutableStateFlow<List<PetCategoryModel>> =
        MutableStateFlow(getPetCategoryList())
    val petCategoryObservable: Flow<List<PetCategoryModel>>
        get() = _petCategoryObservable

    private val petsResource: MutableStateFlow<Resource<List<AnimalResponse>>> =
        MutableStateFlow(Resource.Loading)

    private var specieSelected: EPetCategory = EPetCategory.ALL

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<State> = petsResource
        .flatMapLatest { value ->
            when (value) {
                is Resource.Value -> flowOf(PageUpdate.Value(value.data))
                else -> emptyFlow()
            }
        }
        .map { value -> generateState(value) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            State.Loading
        )

    init {
//        getPets()
        getPetsBySpecie(specieSelected)
    }

    fun refresh() {
//        getPets()
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
//        petsResource.value = Resource.Loading // Trigger the loading state
        getPetsBySpecie(specieSelected)
    }


//    private fun getPetsBySpecie(specie: EPetCategory) {
//        viewModelScope.launch {
//            delay(2.seconds)
//            val response = if (specie != EPetCategory.ALL) {
//                animalsRepository.getAnimalsBySpecie(specie.getPetCategoryString())
//            } else {
//                animalsRepository.getAllAnimals()
//            }
//            response.either(
//                {
//                    Timber.e("Failed to fetch pets for specie: $specie")
//                },
//                { pets ->
//                    Timber.e("Fetched pets for specie: $specie, pets: $pets")
//                    petsResource.value = Resource.Value(pets)
//                }
//            )
//        }
//    }

    private fun getPetsBySpecie(specie: EPetCategory) {
        viewModelScope.launch {
            petsResource.value = Resource.Loading // Set the loading state before making the API request
            delay(2.seconds)
            val response = if (specie != EPetCategory.ALL) {
                animalsRepository.getAnimalsBySpecie(specie.getPetCategoryString())
            } else {
                animalsRepository.getAllAnimals()
            }
            response.either(
                { error ->
                    Timber.e("Failed to fetch pets for specie: $specie, error: $error")
                    petsResource.value = Resource.Value(emptyList()) // Set the empty state in case of failure
                },
                { pets ->
                    Timber.e("Fetched pets for specie: $specie, pets: $pets")
                    petsResource.value = Resource.Value(pets) // Set the value state with the fetched pets
                }
            )
        }
    }

//    private fun getPets() {
//        viewModelScope.launch {
//            delay(10.seconds)
//            if (specieSelected != EPetCategory.ALL) {
//                animalsRepository.getAnimalsBySpecie(specieSelected.getPetCategoryString()).either(
//                    {
//                        Timber.e("failure get all animals")
//                    },
//                    {
//                        Timber.e("success get all animals: $it")
//                    }
//                )
//            } else {
//                animalsRepository.getAllAnimals().either(
//                    {
//                        Timber.e("failure get all animals")
//                    },
//                    {
//                        Timber.e("success get all animals: $it")
//                    }
//                )
//            }
//            petsResource.value = Resource.Value(emptyList())
//        }
//    }

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

//    fun selectPetCategory(petCategoryModel: PetCategoryModel) {
//        val oldData = _petCategoryObservable.value
//        val newData = oldData.map { value ->
//            val isSelected = value.id == petCategoryModel.id
//            value.copy(isSelected = isSelected)
//        }
//        _petCategoryObservable.value = newData
//        specieSelected = EPetCategory.getPetCategoryByIcon(petCategoryModel.iconDrawable)
//    }

    private fun generateState(data: PageUpdate): State {
        return when (data) {
            is PageUpdate.Loading -> State.Loading
            is PageUpdate.Value -> {
                if (data.dataUpdate.isEmpty())
                    return State.Empty
                State.Value(data.dataUpdate)
            }
            else -> State.Empty
        }
    }

    sealed class State {
        object Loading : State()
        object Empty : State()
        data class Value(val petsList: List<AnimalResponse>) : State()
    }
}