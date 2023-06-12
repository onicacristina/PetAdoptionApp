package com.example.petadoptionapp.repository.favorites_repository

import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.repository.local_data_source.SharedPreferencesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

const val FAVORITES = "favorites"

@Singleton
class FavoritesRepository @Inject constructor(
    private val json: Json,
    private val sharedPreferencesDao: SharedPreferencesDao,
) {

    private fun setFavoritesList(list: List<AnimalResponse>) {
        val data = list
        val value = json.encodeToString(data)
        sharedPreferencesDao.saveString(FAVORITES, value)
    }

    fun getFavoritesListObservable(): Flow<List<AnimalResponse>> {
        return sharedPreferencesDao.observeString(FAVORITES).map { value ->
            value ?: return@map null
            return@map json.decodeFromString<List<AnimalResponse>>(value)
        }.filterNotNull()
    }

    fun getFavoritesList(): List<AnimalResponse> {
        val data: List<AnimalResponse>? =
            sharedPreferencesDao.getStringOrNull(FAVORITES)?.let { value ->
                json.decodeFromString<List<AnimalResponse>>(value)
            }
        return data ?: emptyList()
    }

    fun saveToFavorites(animalResponse: AnimalResponse) {
        val oldData = getFavoritesList().filter { value -> value.id != animalResponse.id }
        val newData = listOf(animalResponse).plus(oldData)
        setFavoritesList(newData)
    }

    fun clearAllFavoritesList() {
        setFavoritesList(emptyList())
    }

    fun deleteFromFavoritesList(element: AnimalResponse) {
        val oldData = getFavoritesList()
        val newData = oldData.filter { value -> value.id != element.id }
        setFavoritesList(newData)
    }
}