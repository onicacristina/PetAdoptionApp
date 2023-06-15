package com.example.petadoptionapp.presentation.admin_adoption_centers.add_adoption_center_details

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.Hour
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import com.example.petadoptionapp.repository.hour_repository.HourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAdoptionCenterViewModel @Inject constructor(
    private val hourRepository: HourRepository
) : BaseViewModel(),
    StateDelegate<AddAdoptionCenterViewModel.State> by DefaultStateDelegate(State.default),
    EventDelegate<AddAdoptionCenterViewModel.Event> by DefaultEventDelegate() {


    init {
        subscribeToClickedHourObservables()
        subscribeToClickedEndHourObservables()
    }

    private fun subscribeToClickedHourObservables() {
        viewModelScope.launch {
            hourRepository.clickedHourObservable().onEach { hour ->
                hour?.let {
                    onStartTimeChanged(it)
                }
            }.collect()
        }
    }

    private fun subscribeToClickedEndHourObservables() {
        viewModelScope.launch {
            hourRepository.clickedEndHourObservable().onEach { hour ->
                hour?.let {
                    onEndTimeChanged(it)
                }
            }.collect()
        }
    }

    private fun validateFieldsNotEmpty() {
        val isFieldsNotEmpty = currentState.name.isNotEmpty() &&
                currentState.phoneNumber.isNotEmpty() &&
                currentState.address.isNotEmpty() &&
                currentState.city.isNotEmpty() &&
                currentState.startTime.isNotEmpty() &&
                currentState.endTime.isNotEmpty()
        currentState = currentState.copy(isEnabledButton = isFieldsNotEmpty)
    }


    fun onNameChanges(data: String) {
        currentState = currentState.copy(name = data)
        validateFieldsNotEmpty()
    }

    fun onPhoneNumberChanged(data: String) {
        currentState = currentState.copy(phoneNumber = data)
        validateFieldsNotEmpty()
    }

    fun onAddressChanged(data: String) {
        currentState = currentState.copy(address = data)
        validateFieldsNotEmpty()
    }

    fun onCityChanged(data: String) {
        currentState = currentState.copy(city = data)
        validateFieldsNotEmpty()
    }

    private fun onStartTimeChanged(data: Hour) {
        currentState = currentState.copy(startTime = data.hour)
        validateFieldsNotEmpty()
    }

    private fun onEndTimeChanged(data: Hour) {
        currentState = currentState.copy(endTime = data.hour)
        validateFieldsNotEmpty()
    }

    data class State(
        val name: String,
        val email: String,
        val phoneNumber: String,
        val address: String,
        val city: String,
        val startTime: String,
        val endTime: String,
        val isEnabledButton: Boolean = false
    ) {

        companion object {
            val default: State
                get() = State("", "", "", "", "", "", "")
        }

    }

    enum class Event {
        SUCCESS
    }

}