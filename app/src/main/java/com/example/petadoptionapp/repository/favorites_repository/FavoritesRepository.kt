package com.example.petadoptionapp.repository.favorites_repository

import com.example.petadoptionapp.network.models.Animal
import com.example.petadoptionapp.repository.local_data_source.SharedPreferencesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

const val FAVORITES = "favorites"

@Singleton
class FavoritesRepository @Inject constructor(
    private val json: Json,
    private val sharedPreferencesDao: SharedPreferencesDao,
) {

    private fun setFavoritesList(list: List<Animal>) {
        val data = list
        val value = json.encodeToString(data)
        sharedPreferencesDao.saveString(FAVORITES, value)
    }

    fun getFavoritesListObservable(): Flow<List<Animal>> {
        return sharedPreferencesDao.observeString(FAVORITES).map { value ->
            value ?: return@map null
            return@map json.decodeFromString<List<Animal>>(value)
        }.filterNotNull()
    }

    fun getFavoritesList(): List<Animal> {
        val data: List<Animal>? =
            sharedPreferencesDao.getStringOrNull(FAVORITES)?.let { value ->
                json.decodeFromString<List<Animal>>(value)
            }
        return data ?: emptyList()
    }

    fun saveToFavorites(animalResponse: Animal) {
        val oldData = getFavoritesList().filter { value -> value.id != animalResponse.id }
        val newData = listOf(animalResponse).plus(oldData)
        setFavoritesList(newData)
    }

    fun clearAllFavoritesList() {
        setFavoritesList(emptyList())
    }

    fun deleteFromFavoritesList(element: Animal) {
        val oldData = getFavoritesList()
        val newData = oldData.filter { value -> value.id != element.id }
        setFavoritesList(newData)
    }

    fun isSavedToFavoritesList(animalResponse: Animal): Boolean {
        val favoritesList = getFavoritesList()
        Timber.e("is saved: ${favoritesList.any { it.id == animalResponse.id }}")
        return favoritesList.any { it.id == animalResponse.id }
    }

}