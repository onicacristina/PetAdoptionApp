package com.example.petadoptionapp.presentation.ui.authentication

import com.example.petadoptionapp.R

enum class InfoOrErrorAuthentication(val stringResource: Int? = null) {
    NONE,
    EMAIL_INVALID(R.string.invalid_email_address),
    PASSWORD_LENGTH(R.string.password_length_rule),
    PASSWORD_ONE_UPPERCASE_AND_ONE_NUMBER(R.string.password_rules),
}