package com.example.petadoptionapp.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    private val _petCategoryObservable: MutableStateFlow<List<PetCategoryModel>> =
        MutableStateFlow(getPetCategoryList())
    val petCategoryObservable: Flow<List<PetCategoryModel>>
        get() = _petCategoryObservable

    private val petsResource: MutableStateFlow<Resource<List<AnimalResponse>>> =
        MutableStateFlow(Resource.Loading)

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
        getPets()
    }

    fun refresh() {
        getPets()
    }

    private fun getPets(){
        viewModelScope.launch {
            delay(10.seconds)
            petsResource.value = Resource.Value(emptyList())
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

    fun selectPetCategory(petCategoryModel: PetCategoryModel) {
        val oldData = _petCategoryObservable.value
        val newData = oldData.map { value ->
            val isSelected = value.id == petCategoryModel.id
            value.copy(isSelected = isSelected)
        }
        _petCategoryObservable.value = newData
    }

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