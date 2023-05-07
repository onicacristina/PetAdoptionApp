package com.example.petadoptionapp.presentation.ui.home

import com.example.petadoptionapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    private val _petCategoryObservable: MutableStateFlow<List<PetCategoryModel>> =
        MutableStateFlow(getPetCategoryList())
    val petCategoryObservable: Flow<List<PetCategoryModel>>
        get() = _petCategoryObservable

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
}