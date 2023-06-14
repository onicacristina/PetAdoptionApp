package com.example.petadoptionapp.repository.local_data_source

import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Datasource that saves and reads data from shared preferences
 */
@Singleton
class SharedPreferencesDao @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    /**
     * Saves the given [value] to [key] field
     */
    fun saveString(key: String, value: String) = sharedPreferences.edit().putString(key, value).apply()

    /**
     * Returns the [String] found under the field [key], or null if the [key] does not exist
     */
    fun getStringOrNull(key: String): String? = sharedPreferences.getString(key, null)

    /**
     * Returns the [String] found under the field [key], or [defaultValue] if the [key] does not exist
     */
    fun getString(key: String, defaultValue: String): String = sharedPreferences.getString(key, defaultValue) ?: defaultValue

    /**
     * Observes the changes of [key] field, and maps the key to the value found under the [key] field, at start emits the current value instantly
     */
    fun observeString(key: String): Flow<String?> = keyValueChanges()
        .filter { changedKey -> changedKey == key }
        .map { changedKey ->
            changedKey ?: return@map null
            getStringOrNull(changedKey)
        }
        .onStart { emit(getStringOrNull(key)) }

    /**
     * Saves the given [value] to [key] field
     */
    fun saveInt(key: String, value: Int) = sharedPreferences.edit().putInt(key, value).apply()

    /**
     * Returns the [Int] found under the field [key], or null if the [key] does not exist
     * The [Int.MIN_VALUE] is used to show a null of missing value, this is a limitation of the shared preferences API
     * that we can not give null as the default value
     *
     * If you try to save this exact value, you should use [getInt] function
     */
    fun getIntOrNull(key: String): Int? = sharedPreferences.getInt(key, Int.MIN_VALUE).takeIf { value -> value != Int.MIN_VALUE }

    /**
     * Returns the [Int] found under the field [key], or [defaultValue] if the [key] does not exist
     */
    fun getInt(key: String, defaultValue: Int): Int = sharedPreferences.getInt(key, defaultValue)

    /**
     * Observes the changes of [key] field, and maps the key to the value found under the [key] field, at start emits the current value instantly
     */
    fun observeInt(key: String): Flow<Int?> = keyValueChanges()
        .filter { changedKey -> changedKey == key }
        .map { changedKey ->
            changedKey ?: return@map null
            getIntOrNull(changedKey)
        }
        .onStart { emit(getIntOrNull(key)) }

    /**
     * Saves the given [value] to [key] field
     */
    fun saveLong(key: String, value: Long) = sharedPreferences.edit().putLong(key, value).apply()

    /**
     * Returns the [Int] found under the field [key], or null if the [key] does not exist
     * The [Long.MIN_VALUE] is used to show a null of missing value, this is a limitation of the shared preferences API
     * that we can not give null as the default value
     *
     * If you try to save this exact value, you should use [getLong] function
     */
    fun getLongOrNull(key: String): Long? = sharedPreferences.getLong(key, Long.MIN_VALUE).takeIf { value -> value != Long.MIN_VALUE }

    /**
     * Returns the [Long] found under the field [key], or [defaultValue] if the [key] does not exist
     */
    fun getLong(key: String, defaultValue: Long): Long = sharedPreferences.getLong(key, defaultValue)

    /**
     * Saves the given [value] to [key] field
     */
    fun saveBoolean(key: String, value: Boolean) = sharedPreferences.edit().putBoolean(key, value).apply()

    /**
     * Returns the [Boolean] found under the field [key], or [defaultValue] if the [key] does not exist
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean = sharedPreferences.getBoolean(key, defaultValue)

    /**
     * Observes the changes of [key] field, and maps the key to the value found under the [key] field, at start emits the current value instantly
     */
    fun observeBoolean(key: String, defaultValue: Boolean = false): Flow<Boolean> = keyValueChanges()
        .filter { changedKey -> changedKey == key }
        .map { changedKey ->
            changedKey ?: return@map defaultValue
            getBoolean(changedKey, defaultValue)
        }
        .onStart { emit(getBoolean(key, defaultValue)) }

    /**
     * Deletes the value found under the [key] field
     */
    fun removeValue(key: String) = sharedPreferences.edit().remove(key).apply()

    /**
     * Deletes all shared preferences values
     */
    fun clear() = sharedPreferences.edit().clear().apply()

    /**
     * Returns the content of the whole shared preferences file as a string, useful for debugging
     */
    fun allPrefs(): String? = sharedPreferences.all
        .takeIf { value -> value.isNotEmpty() }
        ?.let { prefs ->
            prefs
                .map { pref -> "${pref.key} : ${pref.value.toString()}" }
                .reduce { sum, value -> "$sum \n$value" }
        }

    /**
     * A flow that observes the changes of the Shared Preferences keys, it is a limitation of the Shared Preferences api that we can not listen for value
     * changes so this flow emits the changed key every time the value under one of the keys changes
     */
    private fun keyValueChanges(): Flow<String?> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key: String? ->
            if (isActive)
                this.trySend(key)
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }

}
