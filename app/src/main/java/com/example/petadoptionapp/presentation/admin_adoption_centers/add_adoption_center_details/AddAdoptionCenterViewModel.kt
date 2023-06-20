package com.example.petadoptionapp.presentation.admin_adoption_centers.add_adoption_center_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.Hour
import com.example.petadoptionapp.network.models.request.NAdoptionCenterParams
import com.example.petadoptionapp.network.models.request.NLinkAdminToAdoptionCenterParam
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import com.example.petadoptionapp.repository.AuthRepository
import com.example.petadoptionapp.repository.adoption_center_repository.AdoptionCenterRepository
import com.example.petadoptionapp.repository.hour_repository.HourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddAdoptionCenterViewModel @Inject constructor(
    private val hourRepository: HourRepository,
    private val authRepository: AuthRepository,
    private val adoptionCenterRepository: AdoptionCenterRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(),
    StateDelegate<AddAdoptionCenterViewModel.State> by DefaultStateDelegate(State.default),
    EventDelegate<AddAdoptionCenterViewModel.Event> by DefaultEventDelegate() {

    private val navArgs: AddAdoptionCenterFragmentArgs by lazy {
        AddAdoptionCenterFragmentArgs.fromSavedStateHandle(savedStateHandle)
    }

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

    fun addAdoptionCenter() {
        viewModelScope.launch {
            val params = NAdoptionCenterParams(
                name = navArgs.userName,
                email = navArgs.userEmail,
                phone = currentState.phoneNumber,
                address = currentState.address,
                city = currentState.city,
                availableStart = currentState.startTime,
                availableEnd = currentState.endTime
            )
            adoptionCenterRepository.addAdoptionCenter(params).fold(
                onSuccess = {
                    Timber.e("success add adoption center")
                    onSuccessAddAdoptionCenter(it.adoptionCenter.id)
                    sendEvent(Event.SUCCESS_ADD_ADOPTION_CENTER)
                },
                onFailure = { error ->
                    Timber.e("error add adoption center")
                    showError(error)
                }
            )
        }
    }

    fun linkAdminUserToAdoptionCenter() {
        viewModelScope.launch {
            val param = NLinkAdminToAdoptionCenterParam(
                adoptionCenterId = currentState.adoptionCenterId
            )
            authRepository.linkAdminUserToAdoptionCenter(param).fold(
                onSuccess = {
                    Timber.e("success link admin to adoptionCenter")
                    sendEvent(Event.SUCCESS_LINK)
                },
                onFailure = { error ->
                    Timber.e("error link admin to adoptionCenter")
                    showError(error)
                }
            )
        }
    }

    private fun validateFieldsNotEmpty() {
        val isFieldsNotEmpty = currentState.phoneNumber.isNotEmpty() &&
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

    private fun onSuccessAddAdoptionCenter(adoptionCenterId: String) {
        currentState = currentState.copy(adoptionCenterId = adoptionCenterId)
    }

    data class State(
        val name: String,
        val email: String,
        val phoneNumber: String,
        val address: String,
        val city: String,
        val startTime: String,
        val endTime: String,
        val adoptionCenterId: String = "",
        val isEnabledButton: Boolean = false
    ) {

        companion object {
            val default: State
                get() = State("", "", "", "", "", "", "")
        }

    }

    enum class Event {
        SUCCESS_ADD_ADOPTION_CENTER,
        SUCCESS_LINK
    }

}