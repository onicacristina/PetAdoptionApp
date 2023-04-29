package com.example.petadoptionapp.presentation.ui.questionnaire.first_question

data class FirstQuestionDetails(
    var phoneNumber: String,
    var city: String,
    var address: String,
    var dateOfBirth: Long
) {
    companion object{
        val default = FirstQuestionDetails("", "", "", 0L)
    }
}
