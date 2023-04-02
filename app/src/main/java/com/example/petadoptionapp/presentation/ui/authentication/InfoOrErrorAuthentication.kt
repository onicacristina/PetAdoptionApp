package com.example.petadoptionapp.presentation.ui.authentication

import com.example.petadoptionapp.R

enum class InfoOrErrorAuthentication(val stringResource: Int? = null) {
    NONE(),
    EMAIL_INVALID(R.string.invalid_email_address)
}