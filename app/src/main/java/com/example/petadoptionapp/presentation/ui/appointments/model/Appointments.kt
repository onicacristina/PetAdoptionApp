package com.example.petadoptionapp.presentation.ui.appointments.model

import com.example.petadoptionapp.network.models.Booking

data class Appointments(
    val upcoming: List<Booking>,
    val past: List<Booking>
)
