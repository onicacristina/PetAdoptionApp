package com.example.petadoptionapp.repository.pet_specie_repository

import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.repository.local_data_source.SpecieCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PetSpecieRepository @Inject constructor(
    private val cache: SpecieCache
) : PetSpecieRepositoryInterface {

    init {
        cache.saveSpecies(getDummyData())
    }

    override suspend fun getSpecieList(): Result<List<EPetCategory>> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                getDummyData()
            }.onSuccess {
                cache.saveSpecies(it)
            }
        }
    }

    override fun specieObservable(): Flow<List<EPetCategory>> {
        return cache.specieObservable()
    }

    override fun clickedSpecieObservable(): Flow<EPetCategory?> {
        return cache.getClickedSpecieObservable()
    }

    override fun setClickedSpecie(specie: EPetCategory?) {
        cache.setClickedSpecie(specie)
    }

    override fun clearCache() {
        cache.clearCache()
    }

    private fun getDummyData(): List<EPetCategory> {
        val dummyData = mutableListOf<EPetCategory>()

        dummyData.add(EPetCategory.ALL)
        dummyData.add(EPetCategory.DOG)
        dummyData.add(EPetCategory.CAT)
        dummyData.add(EPetCategory.BIRD)
        dummyData.add(EPetCategory.RABBIT)

        return dummyData
    }

}