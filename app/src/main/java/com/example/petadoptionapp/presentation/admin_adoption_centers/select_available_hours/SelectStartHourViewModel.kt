package com.example.petadoptionapp.presentation.admin_adoption_centers.select_available_hours

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
class SelectStartHourViewModel @Inject constructor(
    private val hourRepository: HourRepository
) : BaseViewModel(),
    StateDelegate<SelectStartHourViewModel.State> by DefaultStateDelegate(State.Loading),
    EventDelegate<SelectStartHourViewModel.Event> by DefaultEventDelegate() {

    init {
        subscribeToDataObservables()
    }

    private fun subscribeToDataObservables() {
        viewModelScope.launch {
            hourRepository.hourObservable().onEach {
                currentState = State.Value(hours = it)
            }.collect()
        }
    }


    fun setClickedStartHour(hour: Hour) {
        hourRepository.setClickedHour(hour)
        sendEvent(Event.FINISH)
    }

    sealed class State {
        object Loading : State()
        data class Value(val hours: List<Hour>) : State()
    }

    enum class Event {
        FINISH
    }
}