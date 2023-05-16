package com.example.petadoptionapp.presentation.utils

import com.orhanobut.hawk.Hawk


private const val PREFS_TUTORIAL = "prefs_tutorial"

class AppStateFlagsPrefs {

    fun tutorialShown() {
        Hawk.put(PREFS_TUTORIAL, false)
    }

    fun showTutorial() : Boolean {
        return Hawk.get(PREFS_TUTORIAL, true)
    }
}