package com.example.petadoptionapp.presentation.select_user_role

import com.example.petadoptionapp.network.models.EUserRole
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectUserRoleViewModel @Inject constructor() : BaseViewModel(),
    StateDelegate<SelectUserRoleViewModel.State> by DefaultStateDelegate(State.default),
    EventDelegate<SelectUserRoleViewModel.Event> by DefaultEventDelegate() {

    fun onNormalUserSelected() {
        currentState = currentState.copy(normalUserSelected = true, adoptionCenterSelected = false)
    }

    fun onAdoptionCenterSelected() {
        currentState = currentState.copy(normalUserSelected = false, adoptionCenterSelected = true)
    }

    fun onContinueClicked() {
        if (currentState.normalUserSelected || currentState.adoptionCenterSelected)
            sendEvent(Event.SELECTED)
        else
            sendEvent(Event.UNSELECTED)
    }

    fun getSelectedUserType() : EUserRole {
        if (currentState.normalUserSelected)
            return EUserRole.NORMAL_USER
        if (currentState.adoptionCenterSelected)
            return EUserRole.ADOPTION_CENTER_USER
        return EUserRole.NONE
    }

    data class State(
        val normalUserSelected: Boolean,
        val adoptionCenterSelected: Boolean,
    ) {
        companion object {
            val default: State
                get() = State(normalUserSelected = false, adoptionCenterSelected = false)
        }
    }

    enum class Event {
        SELECTED,
        UNSELECTED
    }
}