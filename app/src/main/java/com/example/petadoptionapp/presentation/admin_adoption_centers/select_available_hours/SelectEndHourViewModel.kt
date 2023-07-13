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
class SelectEndHourViewModel @Inject constructor(
    private val hourRepository: HourRepository
) : BaseViewModel(),
    StateDelegate<SelectEndHourViewModel.State> by DefaultStateDelegate(State.Loading),
    EventDelegate<SelectEndHourViewModel.Event> by DefaultEventDelegate() {

    init {
        subscribeToDataObservables()
    }

    private fun subscribeToDataObservables() {
        viewModelScope.launch {
            hourRepository.endHourObservable().onEach {
                currentState = State.Value(hours = it)
            }.collect()
        }
    }


    fun setClickedEndHour(hour: Hour) {
        hourRepository.setClickedEndHour(hour)
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